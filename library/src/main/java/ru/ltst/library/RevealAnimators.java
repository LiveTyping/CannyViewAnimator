package ru.ltst.library;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.view.ViewAnimationUtils;

/**
 * Created by Danil on 09.05.2016.
 */
public class RevealAnimators {

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static class RevealIn extends RevealCanny implements CannyViewAnimator.InAnimator {

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

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static class RevealOut extends RevealCanny implements CannyViewAnimator.OutAnimator {

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

    private static class RevealCanny {
        private final int gravity;

        public RevealCanny(int gravity) {
            this.gravity = gravity;
        }

        @SuppressLint("RtlHardcoded")
        protected int getCenterX(View view) {
            final int horizontalGravity = gravity & Gravity.HORIZONTAL_GRAVITY_MASK;
            if (horizontalGravity == Gravity.LEFT) {
                return 0;
            } else if (horizontalGravity == Gravity.RIGHT) {
                return view.getWidth();
            } else { // (Gravity.CENTER_HORIZONTAL)
                return view.getWidth() / 2;
            }
        }

        protected int getCenterY(View view) {
            final int verticalGravity = gravity & Gravity.VERTICAL_GRAVITY_MASK;
            if (verticalGravity == Gravity.TOP) {
                return 0;
            } else if (verticalGravity == Gravity.BOTTOM) {
                return view.getHeight();
            } else { // (Gravity.CENTER_VERTICAL)
                return view.getHeight() / 2;
            }
        }

        public int getGravity() {
            return gravity;
        }
    }
}
