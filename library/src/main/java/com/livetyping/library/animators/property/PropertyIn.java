package com.livetyping.library.animators.property;

import android.animation.Animator;
import android.animation.PropertyValuesHolder;
import android.util.Property;
import android.view.View;

import com.livetyping.library.interfaces.InAnimator;

public class PropertyIn extends PropertyCanny implements InAnimator {

    public PropertyIn(PropertyValuesHolder... holders) {
        super(holders);
    }

    public PropertyIn(Property<?, Float> property, float start, float end) {
        super(property, start, end);
    }

    public PropertyIn(String propertyName, float start, float end) {
        super(propertyName, start, end);
    }

    @Override
    public Animator getInAnimator(View inChild, View outChild) {
        return getPropertyAnimator(inChild);
    }
}