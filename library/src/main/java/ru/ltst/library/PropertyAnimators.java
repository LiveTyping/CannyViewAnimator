package ru.ltst.library;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.util.Property;
import android.view.View;

/**
 * Created by Danil on 09.05.2016.
 */
public class PropertyAnimators {

    private PropertyAnimators() {
    }

    public static class PropertyIn extends PropertyCanny implements CannyViewAnimator.InAnimator {

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

    public static class PropertyOut extends PropertyCanny implements CannyViewAnimator.OutAnimator {

        public PropertyOut(PropertyValuesHolder... holders) {
            super(holders);
        }

        public PropertyOut(Property<?, Float> property, float start, float end) {
            super(property, start, end);
        }

        public PropertyOut(String propertyName, float start, float end) {
            super(propertyName, start, end);
        }

        @Override
        public Animator getOutAnimator(View inChild, View outChild) {
            return getPropertyAnimator(outChild);
        }
    }

    private static class PropertyCanny {
        private final ValueAnimator propertyAnimator;

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
}
