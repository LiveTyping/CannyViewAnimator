package com.livetyping.library;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.IdRes;
import android.support.annotation.IntDef;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.livetyping.library.animators.property.PropertyAnimators;
import com.livetyping.library.animators.reveal.RevealAnimators;
import com.livetyping.library.interfaces.DefaultCannyAnimators;
import com.livetyping.library.interfaces.InAnimator;
import com.livetyping.library.interfaces.OutAnimator;

public class CannyViewAnimator extends FrameLayout {

    public static final int SEQUENTIALLY = 1;
    public static final int TOGETHER = 2;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({SEQUENTIALLY, TOGETHER})
    @interface AnimateType {
    }

    int indexWhichChild = 0;

    private List<? extends InAnimator> inAnimator;
    private List<? extends OutAnimator> outAnimator;
    private Map<View, Boolean> attachedList;
    private int animateType = SEQUENTIALLY;

    public CannyViewAnimator(Context context) {
        super(context);
        attachedList = new HashMap<>(getChildCount());
    }

    public CannyViewAnimator(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CannyViewAnimator, 0, 0);
        int type = a.getInt(R.styleable.CannyViewAnimator_type, 1);
        int preLollipopIn = a.getInt(R.styleable.CannyViewAnimator_pre_lollipop_in, -1);
        int preLollipopOut = a.getInt(R.styleable.CannyViewAnimator_pre_lollipop_out, -1);
        int in = a.getInt(R.styleable.CannyViewAnimator_in, 0);
        int out = a.getInt(R.styleable.CannyViewAnimator_out, 0);
        a.recycle();
        attachedList = new HashMap<>(getChildCount());
        animateType = type;
        boolean preLollipop = Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP;
        setInAnimator(getAnimators((preLollipop && preLollipopIn != -1) ? preLollipopIn : in));
        setOutAnimator(getAnimators((preLollipop && preLollipopOut != -1) ? preLollipopOut : out));
    }

    private ArrayList<DefaultCannyAnimators> getAnimators(int flags) {
        boolean preLollipop = Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP;
        ArrayList<DefaultCannyAnimators> animators = new ArrayList<>();
        int size = PropertyAnimators.values().length;
        size = preLollipop ? size : size + RevealAnimators.values().length;
        List<DefaultCannyAnimators> defaultAnimators = new ArrayList<>(size);
        defaultAnimators.addAll(Arrays.asList(PropertyAnimators.values()));
        if (!preLollipop)
            defaultAnimators.addAll(Arrays.asList(RevealAnimators.values()));
        for (int i = 0; i < size; i++) {
            if ((flags & (int) Math.pow(2, i)) == Math.pow(2, i)) {
                animators.add(defaultAnimators.get(i));
            }
        }
        return animators;
    }

    public final <T extends InAnimator> void setInAnimator(T... inAnimators) {
        setInAnimator(Arrays.asList(inAnimators));
    }

    public void setInAnimator(List<? extends InAnimator> inAnimators) {
        this.inAnimator = inAnimators;
    }

    public final <T extends OutAnimator> void setOutAnimator(T... outAnimators) {
        setOutAnimator(Arrays.asList(outAnimators));
    }

    public void setOutAnimator(List<? extends OutAnimator> outAnimators) {
        this.outAnimator = outAnimators;
    }

    public void setDisplayedChildId(@IdRes int id) {
        if (getDisplayedChildId() == id) {
            return;
        }
        for (int i = 0, count = getChildCount(); i < count; i++) {
            if (getChildAt(i).getId() == id) {
                setDisplayedChild(i);
                return;
            }
        }
        throw new IllegalArgumentException("No view with ID " + id);
    }

    public void setDisplayedChild(int whichChild) {
        this.indexWhichChild = whichChild;
        if (whichChild >= getChildCount()) {
            this.indexWhichChild = 0;
        } else if (whichChild < 0) {
            this.indexWhichChild = getChildCount() - 1;
        }
        boolean hasFocus = getFocusedChild() != null;
        showOnly(this.indexWhichChild);
        if (hasFocus) {
            requestFocus(FOCUS_FORWARD);
        }
    }

    void showOnly(int inChildIndex) {
        final int count = getChildCount();
        int outChildIndex = 0;
        for (int i = 0; i < count; i++) {
            if (getChildAt(i).getVisibility() == VISIBLE) {
                outChildIndex = i;
                break;
            }
        }
        animate(inChildIndex, outChildIndex);
    }

    //TODO
    private void animate(final int inChildIndex, int outChildIndex) {
        final View inChild = getChildAt(inChildIndex);
        final View outChild = getChildAt(outChildIndex);
        final AnimatorSet inAnimator = this.inAnimator == null ? null
                : mergeAnimators(inChild, outChild, true);
        final AnimatorSet outAnimator = this.outAnimator == null ? null
                : mergeAnimators(inChild, outChild, false);
        if (attachedList.get(outChild) && attachedList.get(inChild) && outAnimator != null) {
            addRestoreListener(outAnimator);
            outAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    outChild.setVisibility(GONE);
                    if (animateType == SEQUENTIALLY) {
                        inChild.setVisibility(VISIBLE);
                        if (inAnimator != null) inAnimator.start();
                    }
                }
            });
            outAnimator.start();
            if (animateType == TOGETHER) {
                inChild.setVisibility(VISIBLE);
                if (inAnimator != null) inAnimator.start();
            }
        } else {
            outChild.setVisibility(GONE);
            inChild.setVisibility(VISIBLE);
            if (inAnimator != null) inAnimator.start();
        }
    }

    private AnimatorSet mergeAnimators(View inChild, View outChild, boolean isIn) {
        AnimatorSet animatorSet = new AnimatorSet();
        List<Animator> animators = new ArrayList<>(inAnimator.size());
        if (isIn) {
            for (InAnimator animator : inAnimator) {
                animators.add(animator.getInAnimator(inChild, outChild));
            }
        } else {
            for (OutAnimator animator : outAnimator) {
                animators.add(animator.getOutAnimator(inChild, outChild));
            }
        }
        animatorSet.playTogether(animators);
        return animatorSet;
    }

    private void addRestoreListener(AnimatorSet animatorSet) {
        for (Animator animator : animatorSet.getChildAnimations()) {
            if (animator instanceof ValueAnimator) {
                animator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        animation.removeListener(this);
                        animation.setDuration(0);
                        ((ValueAnimator) animation).reverse();
                    }
                });
            }
        }
    }

    public int getDisplayedChildId() {
        return getChildAt(getDisplayedChild()).getId();
    }

    public int getDisplayedChild() {
        return indexWhichChild;
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        attachedList.put(child, false);
        child.addOnAttachStateChangeListener(new OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View v) {
                attachedList.put(v, true);
            }

            @Override
            public void onViewDetachedFromWindow(View v) {
                attachedList.put(v, false);
            }
        });
        super.addView(child, index, params);
        if (getChildCount() == 1) {
            child.setVisibility(View.VISIBLE);
        } else {
            child.setVisibility(View.GONE);
        }
        if (index >= 0 && indexWhichChild >= index) {
            setDisplayedChild(indexWhichChild + 1);
        }
    }

    @Override
    public void removeAllViews() {
        super.removeAllViews();
        indexWhichChild = 0;
    }

    @Override
    public void removeView(View view) {
        final int index = indexOfChild(view);
        if (index >= 0) {
            removeViewAt(index);
        }
    }

    @Override
    public void removeViewAt(int index) {
        super.removeViewAt(index);
        final int childCount = getChildCount();
        if (childCount == 0) {
            indexWhichChild = 0;
        } else if (indexWhichChild >= childCount) {
            setDisplayedChild(childCount - 1);
        } else if (indexWhichChild == index) {
            setDisplayedChild(indexWhichChild);
        }
    }

    @Override
    public void removeViewInLayout(View view) {
        removeView(view);
    }

    @Override
    public void removeViews(int start, int count) {
        super.removeViews(start, count);
        if (getChildCount() == 0) {
            indexWhichChild = 0;
        } else if (indexWhichChild >= start && indexWhichChild < start + count) {
            setDisplayedChild(indexWhichChild);
        }
    }

    @Override
    public void removeViewsInLayout(int start, int count) {
        removeViews(start, count);
    }

    public View getCurrentView() {
        return getChildAt(indexWhichChild);
    }

    public int getAnimateType() {
        return animateType;
    }

    public void setAnimateType(@AnimateType int animateType) {
        this.animateType = animateType;
    }
}
