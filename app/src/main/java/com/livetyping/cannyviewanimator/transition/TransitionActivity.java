package com.livetyping.cannyviewanimator.transition;

import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.transition.ChangeBounds;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionSet;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.livetyping.cannyviewanimator.R;
import com.livetyping.library.CannyViewAnimator;
import com.livetyping.library.TransitionViewAnimator;
import com.livetyping.library.animators.property.PropertyAnimators;
import com.livetyping.library.animators.property.PropertyIn;
import com.livetyping.library.interfaces.CannyTransition;

public class TransitionActivity extends AppCompatActivity {
    private CannyViewAnimator animator;
    private TextView startButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition);
        findViews();
        initViews();
    }

    private void findViews(){
        animator = (CannyViewAnimator) findViewById(R.id.transition_animator);
        startButton = (TextView) findViewById(R.id.transition_start_button);
    }

    private void initViews(){
        animator.setCannyTransition(new CannyTransition() {
            @Override
            public Transition getTransition(View inChild, View outChild) {
                TransitionSet transitionSet = new TransitionSet();
                transitionSet.addTransition(new Explode().addTarget(inChild));
                transitionSet.addTransition(new Fade(Fade.OUT).addTarget(outChild));
                return transitionSet;
            }
        });
        animator.setInAnimator(PropertyAnimators.SCALE_X_HALF, PropertyAnimators.SCALE_Y_HALF);
        animator.setOutAnimator(PropertyAnimators.SCALE_X_HALF, PropertyAnimators.SCALE_Y_HALF);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animator.setDisplayedChildIndex(animator.getDisplayedChildIndex() + 1);
            }
        });
    }
}
