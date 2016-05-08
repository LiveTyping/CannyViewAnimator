package ru.ltst.library;

import android.annotation.SuppressLint;
import android.view.Gravity;

@SuppressLint("RtlHardcoded")
public enum DefaultInAnimators implements DefaultCannyAnimators {
    ALPHA(InAnimators.alpha(0, 1)),
    CIRCULAR_REVEAL_TOP(InAnimators.circularReveal(Gravity.TOP | Gravity.CENTER_HORIZONTAL)),
    CIRCULAR_REVEAL_TOP_LEFT(InAnimators.circularReveal(Gravity.TOP | Gravity.LEFT)),
    CIRCULAR_REVEAL_TOP_RIGHT(InAnimators.circularReveal(Gravity.TOP | Gravity.RIGHT)),
    CIRCULAR_REVEAL_BOTTOM(InAnimators.circularReveal(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL)),
    CIRCULAR_REVEAL_BOTTOM_LEFT(InAnimators.circularReveal(Gravity.BOTTOM | Gravity.LEFT)),
    CIRCULAR_REVEAL_BOTTOM_RIGHT(InAnimators.circularReveal(Gravity.BOTTOM | Gravity.RIGHT)),
    CIRCULAR_REVEAL_LEFT(InAnimators.circularReveal(Gravity.LEFT | Gravity.CENTER_VERTICAL)),
    CIRCULAR_REVEAL_RIGHT(InAnimators.circularReveal(Gravity.RIGHT | Gravity.CENTER_VERTICAL));

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
