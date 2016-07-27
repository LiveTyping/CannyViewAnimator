package com.livetyping.cannyviewanimator.custom;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.livetyping.cannyviewanimator.R;
import com.livetyping.library.CannyViewAnimator;
import com.livetyping.library.animators.property.PropertyIn;
import com.livetyping.library.animators.property.PropertyOut;
import com.livetyping.library.interfaces.InAnimator;
import com.livetyping.library.interfaces.OutAnimator;

public class CustomActivity extends AppCompatActivity {
    private CannyViewAnimator animator;
    private TextView startButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom);
        findViews();
        initViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void findViews() {
        animator = (CannyViewAnimator) findViewById(R.id.custom_animator);
        startButton = (TextView) findViewById(R.id.custom_start_button);
    }

    private void initViews() {
        animator.setInAnimator(new InAnimator() {
            @Override
            public Animator getInAnimator(View inChild, View outChild) {
                return ObjectAnimator.ofFloat(inChild, View.TRANSLATION_X, animator.getWidth(), 0)
                        .setDuration(1000);
            }
        });
        animator.setOutAnimator(new OutAnimator() {
            @Override
            public Animator getOutAnimator(View inChild, View outChild) {
                return ObjectAnimator.ofFloat(outChild, View.TRANSLATION_X, 0,
                        -animator.getWidth() / 2).setDuration(1000);
            }
        });
        animator.setLocationType(CannyViewAnimator.IN_ALWAYS_TOP);
        animator.setAnimateType(CannyViewAnimator.TOGETHER);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animator.setDisplayedChildIndex(animator.getDisplayedChildIndex() + 1);
            }
        });
    }
}
