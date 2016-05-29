package ru.ltst.library.animators.reveal;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.os.Build;
import android.view.View;
import android.view.ViewAnimationUtils;

import ru.ltst.library.interfaces.OutAnimator;

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class RevealOut extends RevealCanny implements OutAnimator {

    public RevealOut(int gravity) {
        super(gravity);
    }

    @Override
    public Animator getOutAnimator(View inChild, View outChild) {
        float outRadius = (float) Math.hypot(outChild.getWidth(), outChild.getHeight());
        return ViewAnimationUtils.createCircularReveal(outChild, getCenterX(outChild),
                getCenterY(outChild), outRadius, 0);
    }

}