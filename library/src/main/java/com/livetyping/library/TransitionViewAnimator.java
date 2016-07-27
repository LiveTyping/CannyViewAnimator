package com.livetyping.library;

import android.content.Context;
import android.os.Build;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.util.AttributeSet;
import android.view.View;

import com.livetyping.library.interfaces.CannyTransition;

/**
 * Created by Danil on 16.07.2016.
 */
public class TransitionViewAnimator extends ViewAnimator {
    private CannyTransition cannyTransition;
    private Transition transition;

    public TransitionViewAnimator(Context context) {
        super(context);
    }

    public TransitionViewAnimator(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void changeVisibility(View inChild, View outChild) {
        prepareTransition(inChild, outChild);
        startTransition();
        super.changeVisibility(inChild, outChild);
    }

    protected void prepareTransition(View inChild, View outChild) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && cannyTransition != null) {
            transition = cannyTransition.getTransition(inChild, outChild);
        }
    }

    public void startTransition() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && transition != null) {
            TransitionManager.beginDelayedTransition(this, transition);
        }
    }

    public void setCannyTransition(CannyTransition cannyTransition) {
        this.cannyTransition = cannyTransition;
    }
}
