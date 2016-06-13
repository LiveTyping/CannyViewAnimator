package com.livetyping.library.animators.reveal;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Build;
import android.view.Gravity;
import android.view.View;

import com.livetyping.library.interfaces.DefaultCannyAnimators;
import com.livetyping.library.interfaces.InAnimator;
import com.livetyping.library.interfaces.OutAnimator;

/**
 * Created by Danil on 09.05.2016.
 */
@SuppressLint("RtlHardcoded")
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public enum RevealAnimators implements DefaultCannyAnimators {
    CIRCULAR_REVEAL_TOP_CENTER(new RevealIn(Gravity.TOP | Gravity.CENTER_HORIZONTAL),
            new RevealOut(Gravity.TOP | Gravity.CENTER_HORIZONTAL)),
    CIRCULAR_REVEAL_TOP_LEFT(new RevealIn(Gravity.TOP | Gravity.LEFT),
            new RevealOut(Gravity.TOP | Gravity.LEFT)),
    CIRCULAR_REVEAL_TOP_RIGHT(new RevealIn(Gravity.TOP | Gravity.RIGHT),
            new RevealOut(Gravity.TOP | Gravity.RIGHT)),
    CIRCULAR_REVEAL_BOTTOM_CENTER(new RevealIn(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL),
            new RevealOut(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL)),
    CIRCULAR_REVEAL_BOTTOM_LEFT(new RevealIn(Gravity.BOTTOM | Gravity.LEFT),
            new RevealOut(Gravity.BOTTOM | Gravity.LEFT)),
    CIRCULAR_REVEAL_BOTTOM_RIGHT(new RevealIn(Gravity.BOTTOM | Gravity.RIGHT),
            new RevealOut(Gravity.BOTTOM | Gravity.RIGHT)),
    CIRCULAR_REVEAL_CENTER_LEFT(new RevealIn(Gravity.LEFT | Gravity.CENTER_VERTICAL),
            new RevealOut(Gravity.LEFT | Gravity.CENTER_VERTICAL)),
    CIRCULAR_REVEAL_CENTER_RIGHT(new RevealIn(Gravity.RIGHT | Gravity.CENTER_VERTICAL),
            new RevealOut(Gravity.RIGHT | Gravity.CENTER_VERTICAL)),
    CIRCULAR_REVEAL_CENTER(new RevealIn(Gravity.CENTER),
            new RevealOut(Gravity.CENTER));


    private InAnimator inAnimator;
    private OutAnimator outAnimator;

    RevealAnimators(InAnimator inAnimator,
                    OutAnimator outAnimator) {
        this.inAnimator = inAnimator;
        this.outAnimator = outAnimator;
    }

    @Override
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
