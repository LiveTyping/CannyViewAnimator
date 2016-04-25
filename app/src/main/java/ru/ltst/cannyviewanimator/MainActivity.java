package ru.ltst.cannyviewanimator;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.ltst.library.CannyViewAnimator;

public class MainActivity extends AppCompatActivity implements CannyViewAnimator.InOutAnimators{

    @Bind(R.id.main_animator)
    protected CannyViewAnimator animator;

    private float width;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        ButterKnife.bind(this);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        width = displayMetrics.widthPixels;
        animator.setInOutAnimators(this);
    }

    @OnClick({R.id.main_first_button, R.id.main_second_button, R.id.main_third_button})
    protected void onButtonClick(View view){
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
        ObjectAnimator in = ObjectAnimator.ofFloat(null, View.TRANSLATION_X, -width, 0);
        in.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                View view = (View) ((ObjectAnimator) animation).getTarget();
                view.setTranslationX(0);
            }
        });
        return in;
    }

    @Override
    public Animator getOutAnimator(View inChild, View outChild) {
        ObjectAnimator out = ObjectAnimator.ofFloat(null, View.TRANSLATION_X, 0, width);
        out.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                View view = (View) ((ObjectAnimator) animation).getTarget();
                view.setTranslationX(0);
            }
        });
        return out;
    }
}
