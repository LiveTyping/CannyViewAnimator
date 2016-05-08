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

/**
 * Created by Danil on 08.05.2016.
 */
class OutAnimators {

    public static CannyViewAnimator.OutAnimator alpha(float start, float end) {
        PropertyValuesHolder holder = PropertyValuesHolder.ofFloat(View.ALPHA, start, end);
        return new PropertyOut(holder);
    }

    public static CannyViewAnimator.OutAnimator circularReveal(int gravity) {
        return new RevealOut(gravity);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private static class RevealOut implements CannyViewAnimator.OutAnimator {
        int gravity;

        public RevealOut(int gravity) {
            this.gravity = gravity;
        }

        @Override
        public Animator getOutAnimator(View inChild, View outChild) {
            float outRadius = (float) Math.hypot(outChild.getWidth(), outChild.getHeight());
            return ViewAnimationUtils.createCircularReveal(outChild, getCenterX(gravity, outChild),
                    getCenterY(gravity, outChild), outRadius, 0);
        }

        @SuppressLint("RtlHardcoded")
        private int getCenterX(int gravity, View view) {
            if ((gravity & Gravity.CENTER) == 0 || (gravity & Gravity.CENTER_HORIZONTAL) == 0) {
                return view.getWidth() / 2;
            } else {
                if ((gravity & Gravity.LEFT) == 0) {
                    return 0;
                } else if ((gravity & Gravity.RIGHT) == 0) {
                    return view.getWidth();
                }
            }
            return 0;
        }

        private int getCenterY(int gravity, View view) {
            if ((gravity & Gravity.CENTER) == 0 || (gravity & Gravity.CENTER_VERTICAL) == 0) {
                return view.getHeight() / 2;
            } else {
                if ((gravity & Gravity.TOP) == 0) {
                    return 0;
                } else if ((gravity & Gravity.BOTTOM) == 0) {
                    return view.getHeight();
                }
            }
            return 0;
        }
    }

    public static class PropertyOut implements CannyViewAnimator.OutAnimator {

        PropertyValuesHolder[] holders;

        public PropertyOut(PropertyValuesHolder... holders) {
            this.holders = holders;
        }

        @Override
        public Animator getOutAnimator(View inChild, View outChild) {
            return ObjectAnimator.ofPropertyValuesHolder(outChild, holders);
        }
    }
}
