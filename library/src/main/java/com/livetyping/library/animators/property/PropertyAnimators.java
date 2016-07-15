package com.livetyping.library.animators.property;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.view.View;

import com.livetyping.library.interfaces.DefaultCannyAnimators;
import com.livetyping.library.interfaces.InAnimator;
import com.livetyping.library.interfaces.OutAnimator;

@SuppressLint("RtlHardcoded")
public enum PropertyAnimators implements DefaultCannyAnimators {
    EMPTY(new PropertyIn(), new PropertyOut()),
    //alpha
    ALPHA(new PropertyIn(View.ALPHA, 0, 1), new PropertyOut(View.ALPHA, 1, 0)),
    ALPHA_HALF(new PropertyIn(View.ALPHA, 0.5f, 1), new PropertyOut(View.ALPHA, 1, 0.5f)),
    //scale
    SCALE_X(new PropertyIn(View.SCALE_X, 0, 1), new PropertyOut(View.SCALE_X, 1, 0)),
    SCALE_X_HALF(new PropertyIn(View.SCALE_X, 0.5f, 1), new PropertyOut(View.SCALE_X, 1, 0.5f)),
    SCALE_Y(new PropertyIn(View.SCALE_Y, 0, 1), new PropertyOut(View.SCALE_Y, 1, 0)),
    SCALE_Y_HALF(new PropertyIn(View.SCALE_Y, 0.5f, 1), new PropertyOut(View.SCALE_Y, 1, 0.5f)),
    //rotation
    ROTATION_90(new PropertyIn(View.ROTATION, 90, 0), new PropertyOut(View.ROTATION, 0, 90)),
    ROTATION_180(new PropertyIn(View.ROTATION, 180, 0), new PropertyOut(View.ROTATION, 0, 180)),
    ROTATION_360(new PropertyIn(View.ROTATION, 360, 0), new PropertyOut(View.ROTATION, 0, 360)),
    ROTATION_M90(new PropertyIn(View.ROTATION, -90, 0), new PropertyOut(View.ROTATION, 0, -90)),
    ROTATION_M180(new PropertyIn(View.ROTATION, -180, 0), new PropertyOut(View.ROTATION, 0, -180)),
    ROTATION_M360(new PropertyIn(View.ROTATION, -360, 0), new PropertyOut(View.ROTATION, 0, -360)),
    //rotation x
    ROTATION_X_90(new PropertyIn(View.ROTATION_X, 90, 0), new PropertyOut(View.ROTATION_X, 0, 90)),
    ROTATION_X_180(new PropertyIn(View.ROTATION_X, 180, 0), new PropertyOut(View.ROTATION_X, 0, 180)),
    ROTATION_X_360(new PropertyIn(View.ROTATION_X, 360, 0), new PropertyOut(View.ROTATION_X, 0, 360)),
    ROTATION_X_M90(new PropertyIn(View.ROTATION_X, -90, 0), new PropertyOut(View.ROTATION_X, 0, -90)),
    ROTATION_X_M180(new PropertyIn(View.ROTATION_X, -180, 0), new PropertyOut(View.ROTATION_X, 0, -180)),
    ROTATION_X_M360(new PropertyIn(View.ROTATION_X, -360, 0), new PropertyOut(View.ROTATION_X, 0, -360)),
    //rotation y
    ROTATION_Y_90(new PropertyIn(View.ROTATION_Y, 90, 0), new PropertyOut(View.ROTATION_Y, 0, 90)),
    ROTATION_Y_180(new PropertyIn(View.ROTATION_Y, 180, 0), new PropertyOut(View.ROTATION_Y, 0, 180)),
    ROTATION_Y_360(new PropertyIn(View.ROTATION_Y, 360, 0), new PropertyOut(View.ROTATION_Y, 0, 360)),
    ROTATION_Y_M90(new PropertyIn(View.ROTATION_Y, -90, 0), new PropertyOut(View.ROTATION_Y, 0, -90)),
    ROTATION_Y_M180(new PropertyIn(View.ROTATION_Y, -180, 0), new PropertyOut(View.ROTATION_Y, 0, -180)),
    ROTATION_Y_M360(new PropertyIn(View.ROTATION_Y, -360, 0), new PropertyOut(View.ROTATION_Y, 0, -360));
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
