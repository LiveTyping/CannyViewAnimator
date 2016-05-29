package ru.ltst.library.animators.property;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.util.Property;
import android.view.View;

class PropertyCanny {
    Animator propertyAnimator;

    public PropertyCanny(PropertyValuesHolder... holders) {
        this.propertyAnimator = ObjectAnimator.ofPropertyValuesHolder(holders);
    }

    public PropertyCanny(Property<?, Float> property, float start, float end) {
        this.propertyAnimator = ObjectAnimator.ofFloat(null, property, start, end);
    }

    public PropertyCanny(String propertyName, float start, float end) {
        this.propertyAnimator = ObjectAnimator.ofFloat(null, propertyName, start, end);
    }

    public Animator getPropertyAnimator(View child) {
        propertyAnimator.setTarget(child);
        return propertyAnimator.clone();
    }
}