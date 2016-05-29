package ru.ltst.library.interfaces;

import android.animation.Animator;
import android.view.View;

public interface InAnimator {
    Animator getInAnimator(View inChild, View outChild);
}