package com.livetyping.library.animators.property;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.view.View;

import com.livetyping.library.interfaces.DefaultCannyAnimators;
import com.livetyping.library.interfaces.InAnimator;
import com.livetyping.library.interfaces.OutAnimator;

@SuppressLint("RtlHardcoded")
public enum PropertyAnimators implements DefaultCannyAnimators {
    NULL(null, null),
    //alpha
    ALPHA(new PropertyIn(View.ALPHA, 0, 1), new PropertyOut(View.ALPHA, 1, 0)),
    ALPHA_HALF(new PropertyIn(View.ALPHA, 0.5f, 1), new PropertyOut(View.ALPHA, 1, 0.5f)),
    //scale
    SCALE_X(new PropertyIn(View.SCALE_X, 0, 1), new PropertyOut(View.SCALE_X, 1, 0)),
    SCALE_X_HALF(new PropertyIn(View.SCALE_X, 0.5f, 1), new PropertyOut(View.SCALE_X, 1, 0.5f)),
    SCALE_Y(new PropertyIn(View.SCALE_Y, 0, 1), new PropertyOut(View.SCALE_Y, 1, 0)),
    SCALE_Y_HALF(new PropertyIn(View.SCALE_Y, 0.5f, 1), new PropertyOut(View.SCALE_Y, 1, 0.5f));

    private InAnimator inAnimator;
    private OutAnimator outAnimator;

    PropertyAnimators(InAnimator inAnimator,
                      OutAnimator outAnimator) {
        this.inAnimator = inAnimator;
        this.outAnimator = outAnimator;
    }

    public String getName() {
        return name();
    }


    @Override
    public Animator getInAnimator(View inChild, View outChild) {
        return inAnimator.getInAnimator(inChild, outChild);
    }

    @Override
    public Animator getOutAnimator(View inChild, View outChild) {
        return outAnimator.getOutAnimator(inChild, outChild);
    }
}
