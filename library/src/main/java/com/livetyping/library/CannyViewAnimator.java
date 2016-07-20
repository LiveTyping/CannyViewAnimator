package com.livetyping.library;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.IntDef;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.livetyping.library.animators.property.PropertyAnimators;
import com.livetyping.library.animators.reveal.RevealAnimators;
import com.livetyping.library.interfaces.DefaultCannyAnimators;
import com.livetyping.library.interfaces.InAnimator;
import com.livetyping.library.interfaces.OutAnimator;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CannyViewAnimator extends TransitionViewAnimator {

    public static final int SEQUENTIALLY = 1;
    public static final int TOGETHER = 2;
    private int animateType = SEQUENTIALLY;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({SEQUENTIALLY, TOGETHER})
    @interface AnimateType {
    }


    public static final int FOR_POSITION = 1;
    public static final int IN_ALWAYS_TOP = 2;
    public static final int OUT_ALWAYS_TOP = 3;
    private int locationType = FOR_POSITION;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({FOR_POSITION, IN_ALWAYS_TOP, OUT_ALWAYS_TOP})
    @interface LocationType {
    }

    private List<? extends InAnimator> inAnimator;
    private List<? extends OutAnimator> outAnimator;
    private final Map<View, Boolean> attachedList = new HashMap<>(getChildCount());


    public CannyViewAnimator(Context context) {
        super(context);
    }

    public CannyViewAnimator(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CannyViewAnimator, 0, 0);
        int animateType = a.getInt(R.styleable.CannyViewAnimator_animate_type, 1);
        int locationType = a.getInt(R.styleable.CannyViewAnimator_location_type, 1);
        int preLollipopIn = a.getInt(R.styleable.CannyViewAnimator_pre_lollipop_in, -1);
        int preLollipopOut = a.getInt(R.styleable.CannyViewAnimator_pre_lollipop_out, -1);
        int in = a.getInt(R.styleable.CannyViewAnimator_in, 0);
        int out = a.getInt(R.styleable.CannyViewAnimator_out, 0);
        a.recycle();
        this.animateType = animateType;
        this.locationType = locationType;
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

    @SafeVarargs
    public final <T extends InAnimator> void setInAnimator(T... inAnimators) {
        setInAnimator(Arrays.asList(inAnimators));
    }

    public void setInAnimator(List<? extends InAnimator> inAnimators) {
        this.inAnimator = inAnimators;
    }

    @SafeVarargs
    public final <T extends OutAnimator> void setOutAnimator(T... outAnimators) {
        setOutAnimator(Arrays.asList(outAnimators));
    }

    public void setOutAnimator(List<? extends OutAnimator> outAnimators) {
        this.outAnimator = outAnimators;
    }

    @Override
    protected void changeVisibility(View inChild, View outChild) {
        if (attachedList.get(outChild) && attachedList.get(inChild)) {
            AnimatorSet animatorSet = new AnimatorSet();
            Animator inAnimator = mergeInAnimators(inChild, outChild);
            Animator outAnimator = mergeOutAnimators(inChild, outChild);
            prepareTransition(inChild, outChild);

            switch (animateType) {
                case SEQUENTIALLY:
                    animatorSet.playSequentially(outAnimator, inAnimator);
                    break;
                case TOGETHER:
                    animatorSet.playTogether(outAnimator, inAnimator);
                    break;
            }

            switch (locationType) {
                case FOR_POSITION:
                    addOnStartVisibleListener(inAnimator, inChild);
                    addOnEndInvisibleListener(outAnimator, outChild);
                    break;
                case IN_ALWAYS_TOP:
                    addOnStartVisibleListener(inAnimator, inChild);
                    addOnEndInvisibleListener(inAnimator, outChild);
                    addOnStartToTopOnEndToInitPositionListener(inAnimator, inChild);
                    break;
                case OUT_ALWAYS_TOP:
                    addOnStartVisibleListener(outAnimator, inChild);
                    addOnEndInvisibleListener(outAnimator, outChild);
                    addOnStartToTopOnEndToInitPositionListener(outAnimator, outChild);
                    break;
            }
            animatorSet.start();
        } else {
            super.changeVisibility(inChild, outChild);
        }
    }

    private AnimatorSet mergeInAnimators(final View inChild, final View outChild) {
        AnimatorSet animatorSet = new AnimatorSet();
        List<Animator> animators = new ArrayList<>(inAnimator.size());
        for (InAnimator inAnimator : this.inAnimator) {
            if (inAnimator != null) {
                Animator animator = inAnimator.getInAnimator(inChild, outChild);
                if (animator != null) {
                    animators.add(animator);
                }
            }
        }
        animatorSet.playTogether(animators);
//        animatorSet.addListener(new AnimatorListenerAdapter() {
//            @Override
//            public void onAnimationStart(Animator animation) {
//                prepareTransition(inChild, outChild);
//                inChild.setVisibility(VISIBLE);
//                int childCount = getChildCount();
//                bringChildToPosition(inChild, childCount - 1);
//            }
//
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                bringChildToPosition(inChild, getDisplayedChildIndex());
//            }
//        });
        return animatorSet;
    }

    private AnimatorSet mergeOutAnimators(final View inChild, final View outChild) {
        AnimatorSet animatorSet = new AnimatorSet();
        List<Animator> animators = new ArrayList<>(outAnimator.size());
        for (OutAnimator outAnimator : this.outAnimator) {
            if (outAnimator != null) {
                Animator animator = outAnimator.getOutAnimator(inChild, outChild);
                if (animator != null)
                    animators.add(animator);
            }
        }
        animatorSet.playTogether(animators);
//        animatorSet.addListener(new AnimatorListenerAdapter() {
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                prepareTransition(inChild, outChild);
//                outChild.setVisibility(INVISIBLE);
//            }
//        });
        addRestoreInitValuesListener(animatorSet);
        return animatorSet;
    }

    private void addRestoreInitValuesListener(AnimatorSet animatorSet) {
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

    private void addOnStartVisibleListener(Animator animator, final View view) {
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                startTransition();
                view.setVisibility(VISIBLE);
            }
        });
    }

    private void addOnEndInvisibleListener(Animator animator, final View view) {
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                startTransition();
                view.setVisibility(INVISIBLE);
            }
        });
    }

    private void addOnStartToTopOnEndToInitPositionListener(Animator animator, final View view) {
        final int initLocation = indexOfChild(view);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                bringChildToPosition(view, getChildCount() - 1);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                bringChildToPosition(view, initLocation);
            }
        });
    }

    public int getAnimateType() {
        return animateType;
    }

    public void setAnimateType(@AnimateType int animateType) {
        this.animateType = animateType;
    }

    public int getLocationType() {
        return locationType;
    }

    public void setLocationType(@LocationType int locationType) {
        this.locationType = locationType;
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
    }

    @Override
    public void removeAllViews() {
        attachedList.clear();
        super.removeAllViews();
    }

    @Override
    public void removeView(View view) {
        attachedList.remove(view);
        super.removeView(view);
    }

    @Override
    public void removeViewAt(int index) {
        attachedList.remove(getChildAt(index));
        super.removeViewAt(index);
    }

    @Override
    public void removeViews(int start, int count) {
        for (int i = start; i < start + count; i++) {
            attachedList.remove(getChildAt(i));
        }
        super.removeViews(start, count);
    }

}
