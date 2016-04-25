package ru.ltst.library;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.LayoutTransition;
import android.content.Context;
import android.support.annotation.IdRes;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

public class CannyViewAnimator extends FrameLayout {

    int indexWhichChild = 0;

    private InOutAnimators inOutAnimators;
    private LayoutTransition layoutTransition;

    public CannyViewAnimator(Context context) {
        super(context);
        layoutTransition = new LayoutTransition();
        setLayoutTransition(layoutTransition);
    }

    public CannyViewAnimator(Context context, AttributeSet attrs) {
        super(context, attrs);
        layoutTransition = new LayoutTransition();
        setLayoutTransition(layoutTransition);
    }

    public void setInOutAnimators(InOutAnimators inOutAnimators) {
        this.inOutAnimators = inOutAnimators;
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
        if (inOutAnimators != null) {
            animate(inChildIndex, outChildIndex);
        }
    }

    private void animate(final int inChildIndex, int outChildIndex) {
        final View inChild = getChildAt(inChildIndex);
        final View outChild = getChildAt(outChildIndex);
        final Animator inAnimator = inOutAnimators.getInAnimator(inChild, outChild);
        final Animator outAnimator = inOutAnimators.getOutAnimator(inChild, outChild);
        layoutTransition.setAnimator(LayoutTransition.APPEARING, inAnimator);
        layoutTransition.setAnimator(LayoutTransition.DISAPPEARING, outAnimator);
        if (outAnimator != null) {
            outAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    inChild.setVisibility(View.VISIBLE);
                }
            });
            outChild.setVisibility(View.GONE);
        } else {
            outChild.setVisibility(View.GONE);
            inChild.setVisibility(View.VISIBLE);
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

    public interface InOutAnimators {
        Animator getInAnimator(View inChild, View outChild);

        Animator getOutAnimator(View inChild, View outChild);
    }
}
