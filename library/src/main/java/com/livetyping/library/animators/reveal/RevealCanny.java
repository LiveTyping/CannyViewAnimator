package com.livetyping.library.animators.reveal;

import android.annotation.SuppressLint;
import android.view.Gravity;
import android.view.View;

class RevealCanny {
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