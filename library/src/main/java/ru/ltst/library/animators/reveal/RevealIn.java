package ru.ltst.library.animators.reveal;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.os.Build;
import android.view.View;
import android.view.ViewAnimationUtils;

import ru.ltst.library.interfaces.InAnimator;

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class RevealIn extends RevealCanny implements InAnimator {

    public RevealIn(int gravity) {
        super(gravity);
    }

    @Override
    public Animator getInAnimator(View inChild, View outChild) {
        float inRadius = (float) Math.hypot(inChild.getWidth(), inChild.getHeight());
        return ViewAnimationUtils.createCircularReveal(inChild, getCenterX(inChild),
                getCenterY(inChild), 0, inRadius);
    }

}