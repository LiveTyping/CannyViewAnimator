package ru.ltst.library;

import android.annotation.SuppressLint;
import android.view.Gravity;

@SuppressLint("RtlHardcoded")
public enum DefaultOutAnimators implements DefaultCannyAnimators {
    ALPHA(OutAnimators.alpha(1, 0)),
    CIRCULAR_REVEAL_TOP(OutAnimators.circularReveal(Gravity.TOP | Gravity.CENTER_HORIZONTAL)),
    CIRCULAR_REVEAL_TOP_LEFT(OutAnimators.circularReveal(Gravity.TOP | Gravity.LEFT)),
    CIRCULAR_REVEAL_TOP_RIGHT(OutAnimators.circularReveal(Gravity.TOP | Gravity.RIGHT)),
    CIRCULAR_REVEAL_BOTTOM(OutAnimators.circularReveal(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL)),
    CIRCULAR_REVEAL_BOTTOM_LEFT(OutAnimators.circularReveal(Gravity.BOTTOM | Gravity.LEFT)),
    CIRCULAR_REVEAL_BOTTOM_RIGHT(OutAnimators.circularReveal(Gravity.BOTTOM | Gravity.RIGHT)),
    CIRCULAR_REVEAL_LEFT(OutAnimators.circularReveal(Gravity.LEFT | Gravity.CENTER_VERTICAL)),
    CIRCULAR_REVEAL_RIGHT(OutAnimators.circularReveal(Gravity.RIGHT | Gravity.CENTER_VERTICAL));

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
