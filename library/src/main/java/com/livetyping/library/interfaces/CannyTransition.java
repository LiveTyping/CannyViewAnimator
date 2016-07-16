package com.livetyping.library.interfaces;

import android.animation.Animator;
import android.transition.Transition;
import android.view.View;

public interface CannyTransition {
    Transition getTransition(View inChild, View outChild);
}