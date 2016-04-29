package ru.ltst.cannyviewanimator;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

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

    @Override
    public Animator getInAnimator(View inChild, View outChild) {
        PropertyValuesHolder scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y.getName(), 0.4f, 1);
        PropertyValuesHolder scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X.getName(), 0.4f, 1);
        return ObjectAnimator.ofPropertyValuesHolder(inChild, scaleX, scaleY);
    }

    @Override
    public Animator getOutAnimator(View inChild, View outChild) {
        PropertyValuesHolder scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y.getName(), 1, 0.4f);
        PropertyValuesHolder scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X.getName(), 1, 0.4f);
        return ObjectAnimator.ofPropertyValuesHolder(outChild, scaleX, scaleY);
    }
}
