package ru.ltst.cannyviewanimator;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewAnimationUtils;

import ru.ltst.library.CannyViewAnimator;

public class MainActivity extends AppCompatActivity implements CannyViewAnimator.Animators,
        View.OnClickListener {

    private CannyViewAnimator animator;
    private int buttons[] = new int[]{
            R.id.main_first_button,
            R.id.main_second_button,
            R.id.main_third_button};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        initViews();
    }

    private void initViews() {
        animator = (CannyViewAnimator) findViewById(R.id.main_animator);
        if (animator != null) animator.setAnimators(this);
        for (int id : buttons) {
            View view = findViewById(id);
            if (view != null) view.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_first_button:
                animator.setDisplayedChildId(R.id.main_first_view);
                break;
            case R.id.main_second_button:
                animator.setDisplayedChildId(R.id.main_second_view);
                break;
            case R.id.main_third_button:
                animator.setDisplayedChildId(R.id.main_third_view);
                break;
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public Animator getInAnimator(View inChild, View outChild) {
        //animator set
//        AnimatorSet in = new AnimatorSet();
//        in.play(ObjectAnimator.ofFloat(inChild, View.SCALE_Y, 0.4f, 1))
//                .with(ObjectAnimator.ofFloat(inChild, View.SCALE_X, 0.4f, 1));
//        return in;
        //holder
//        PropertyValuesHolder scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y.getName(), 0.4f, 1);
//        PropertyValuesHolder scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X.getName(), 0.4f, 1);
//        return ObjectAnimator.ofPropertyValuesHolder(inChild, scaleX, scaleY);
        //reveral
        float inRadius = (float) Math.hypot(inChild.getWidth(), inChild.getHeight());
        return ViewAnimationUtils.createCircularReveal(inChild, 0, 0, 0, inRadius);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public Animator getOutAnimator(View inChild, View outChild) {
        //animator set
//        AnimatorSet out = new AnimatorSet();
//        out.play(ObjectAnimator.ofFloat(outChild, View.SCALE_Y, 1, 0.4f))
//                .with(ObjectAnimator.ofFloat(outChild, View.SCALE_X, 1, 0.4f));
//        return out;
        //holder
//        PropertyValuesHolder scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y.getName(), 1, 0.4f);
//        PropertyValuesHolder scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X.getName(), 1, 0.4f);
//        return ObjectAnimator.ofPropertyValuesHolder(outChild, scaleX, scaleY);
        //reveral
        float outRadius = (float) Math.hypot(outChild.getWidth(), outChild.getHeight());
        return ViewAnimationUtils.createCircularReveal(outChild, 0, 0, outRadius, 0);
    }
}
