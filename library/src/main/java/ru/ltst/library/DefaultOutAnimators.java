package ru.ltst.library;

import android.annotation.SuppressLint;
import android.view.Gravity;
import android.view.View;

@SuppressLint("RtlHardcoded")
public enum DefaultOutAnimators implements DefaultCannyAnimators {
    NULL(null),
    //alpha
    ALPHA(new PropertyAnimators.PropertyOut(View.ALPHA, 1, 0)),
    ALPHA_HALF(new PropertyAnimators.PropertyOut(View.ALPHA, 1, 0.5f)),
    //scale
    SCALE_X(new PropertyAnimators.PropertyOut(View.SCALE_X, 1, 0)),
    SCALE_X_HALF(new PropertyAnimators.PropertyOut(View.SCALE_X, 1, 0.5f)),
    SCALE_Y(new PropertyAnimators.PropertyOut(View.SCALE_Y, 1, 0)),
    SCALE_Y_HALF(new PropertyAnimators.PropertyOut(View.SCALE_Y, 1, 0.5f)),
    //reveal
    CIRCULAR_REVEAL_TOP(new RevealAnimators.RevealOut(Gravity.TOP | Gravity.CENTER_HORIZONTAL)),
    CIRCULAR_REVEAL_TOP_LEFT(new RevealAnimators.RevealOut(Gravity.TOP | Gravity.LEFT)),
    CIRCULAR_REVEAL_TOP_RIGHT(new RevealAnimators.RevealOut(Gravity.TOP | Gravity.RIGHT)),
    CIRCULAR_REVEAL_BOTTOM(new RevealAnimators.RevealOut(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL)),
    CIRCULAR_REVEAL_BOTTOM_LEFT(new RevealAnimators.RevealOut(Gravity.BOTTOM | Gravity.LEFT)),
    CIRCULAR_REVEAL_BOTTOM_RIGHT(new RevealAnimators.RevealOut(Gravity.BOTTOM | Gravity.RIGHT)),
    CIRCULAR_REVEAL_LEFT(new RevealAnimators.RevealOut(Gravity.LEFT | Gravity.CENTER_VERTICAL)),
    CIRCULAR_REVEAL_RIGHT(new RevealAnimators.RevealOut(Gravity.RIGHT | Gravity.CENTER_VERTICAL)),
    CIRCULAR_REVEAL_CENTER(new RevealAnimators.RevealOut(Gravity.CENTER));

    private CannyViewAnimator.OutAnimator outAnimator;

    DefaultOutAnimators(CannyViewAnimator.OutAnimator outAnimator) {
        this.outAnimator = outAnimator;
    }

    public String getName() {
        return name();
    }

    @Override
    public CannyViewAnimator.InAnimator getInAnimator() {
        return null;
    }

    public CannyViewAnimator.OutAnimator getOutAnimator() {
        return outAnimator;
    }

}
