package ru.ltst.library.animators.property;

import android.annotation.SuppressLint;
import android.view.View;

import java.util.ArrayList;

import ru.ltst.library.CannyViewAnimator;
import ru.ltst.library.DefaultCannyAnimators;

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

    private CannyViewAnimator.InAnimator inAnimator;
    private CannyViewAnimator.OutAnimator outAnimator;

    PropertyAnimators(CannyViewAnimator.InAnimator inAnimator,
                      CannyViewAnimator.OutAnimator outAnimator) {
        this.inAnimator = inAnimator;
        this.outAnimator = outAnimator;
    }

    public String getName() {
        return name();
    }

    @Override
    public CannyViewAnimator.InAnimator getInAnimator() {
        return inAnimator;
    }

    public CannyViewAnimator.OutAnimator getOutAnimator() {
        return outAnimator;
    }
}
