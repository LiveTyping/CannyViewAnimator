package ru.ltst.library;

import android.annotation.SuppressLint;
import android.view.Gravity;
import android.view.View;

@SuppressLint("RtlHardcoded")
public enum DefaultInAnimators implements DefaultCannyAnimators {
    NULL(null),
    //alpha
    ALPHA(new PropertyAnimators.PropertyIn(View.ALPHA, 0, 1)),
    ALPHA_HALF(new PropertyAnimators.PropertyIn(View.ALPHA, 0.5f, 1)),
    //scale
    SCALE_X(new PropertyAnimators.PropertyIn(View.SCALE_X, 0, 1)),
    SCALE_X_HALF(new PropertyAnimators.PropertyIn(View.SCALE_X, 0.5f, 1)),
    SCALE_Y(new PropertyAnimators.PropertyIn(View.SCALE_Y, 0, 1)),
    SCALE_Y_HALF(new PropertyAnimators.PropertyIn(View.SCALE_Y, 0.5f, 1)),
    //reveal
    CIRCULAR_REVEAL_TOP(new RevealAnimators.RevealIn(Gravity.TOP | Gravity.CENTER_HORIZONTAL)),
    CIRCULAR_REVEAL_TOP_LEFT(new RevealAnimators.RevealIn(Gravity.TOP | Gravity.LEFT)),
    CIRCULAR_REVEAL_TOP_RIGHT(new RevealAnimators.RevealIn(Gravity.TOP | Gravity.RIGHT)),
    CIRCULAR_REVEAL_BOTTOM(new RevealAnimators.RevealIn(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL)),
    CIRCULAR_REVEAL_BOTTOM_LEFT(new RevealAnimators.RevealIn(Gravity.BOTTOM | Gravity.LEFT)),
    CIRCULAR_REVEAL_BOTTOM_RIGHT(new RevealAnimators.RevealIn(Gravity.BOTTOM | Gravity.RIGHT)),
    CIRCULAR_REVEAL_LEFT(new RevealAnimators.RevealIn(Gravity.LEFT | Gravity.CENTER_VERTICAL)),
    CIRCULAR_REVEAL_RIGHT(new RevealAnimators.RevealIn(Gravity.RIGHT | Gravity.CENTER_VERTICAL)),
    CIRCULAR_REVEAL_CENTER(new RevealAnimators.RevealIn(Gravity.CENTER));

    private CannyViewAnimator.InAnimator inAnimator;

    DefaultInAnimators(CannyViewAnimator.InAnimator inAnimator) {
        this.inAnimator = inAnimator;
    }

    @Override
    public String getName() {
        return name();
    }

    public CannyViewAnimator.InAnimator getInAnimator() {
        return inAnimator;
    }

    @Override
    public CannyViewAnimator.OutAnimator getOutAnimator() {
        return null;
    }
}
