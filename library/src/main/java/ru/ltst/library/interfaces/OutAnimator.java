package ru.ltst.library.interfaces;

import android.animation.Animator;
import android.view.View;

public interface OutAnimator {
    Animator getOutAnimator(View inChild, View outChild);
}