package ru.ltst.library;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.view.ViewAnimationUtils;

class InAnimators {

    public static CannyViewAnimator.InAnimator alpha(float start, float end) {
        PropertyValuesHolder holder = PropertyValuesHolder.ofFloat(View.ALPHA, start, end);
        return new PropertyIn(holder);
    }

    public static CannyViewAnimator.InAnimator circularReveal(int gravity) {
        return new RevealIn(gravity);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private static class RevealIn implements CannyViewAnimator.InAnimator {
        int gravity;

        public RevealIn(int gravity) {
            this.gravity = gravity;
        }

        @Override
        public Animator getInAnimator(View inChild, View outChild) {
            float inRadius = (float) Math.hypot(inChild.getWidth(), inChild.getHeight());
            return ViewAnimationUtils.createCircularReveal(inChild, getCenterX(gravity, inChild),
                    getCenterY(gravity, inChild), 0, inRadius);
        }

        @SuppressLint("RtlHardcoded")
        private int getCenterX(int gravity, View view){
            if ((gravity & Gravity.CENTER) == 0 || (gravity & Gravity.CENTER_HORIZONTAL) == 0) {
                return view.getWidth() / 2;
            } else {
                if ((gravity & Gravity.LEFT) == 0) {
                    return  0;
                } else if ((gravity & Gravity.RIGHT) == 0) {
                    return view.getWidth();
                }
            }
            return 0;
        }

        private int getCenterY(int gravity, View view){
            if ((gravity & Gravity.CENTER) == 0 || (gravity & Gravity.CENTER_VERTICAL) == 0) {
                return view.getHeight() / 2;
            } else {
                if ((gravity & Gravity.TOP) == 0) {
                    return  0;
                } else if ((gravity & Gravity.BOTTOM) == 0) {
                    return view.getHeight();
                }
            }
            return 0;
        }
    }

    private static class PropertyIn implements CannyViewAnimator.InAnimator {

        PropertyValuesHolder[] holders;

        public PropertyIn(PropertyValuesHolder... holders) {
            this.holders = holders;
        }

        @Override
        public Animator getInAnimator(View inChild, View outChild) {
            return ObjectAnimator.ofPropertyValuesHolder(inChild, holders);
        }
    }
}
