package com.livetyping.cannyviewanimator.choose;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.livetyping.cannyviewanimator.R;
import com.livetyping.cannyviewanimator.RequestResultCodes;
import com.livetyping.cannyviewanimator.choose.list.ChooseListActivity;
import com.livetyping.library.CannyViewAnimator;
import com.livetyping.library.animators.property.PropertyAnimators;
import com.livetyping.library.animators.reveal.RevealAnimators;
import com.livetyping.library.interfaces.DefaultCannyAnimators;

import java.util.ArrayList;
import java.util.List;

public class ChooseActivity extends AppCompatActivity {

    private CannyViewAnimator animator;
    private TextView inButton, outButton, startButton;
    private SwitchCompat typeCheck;
    private FrameLayout checkContainer;
    private RadioGroup locationTypeChooser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_activity);
        findViews();
        initViews();
    }

    private void findViews() {
        animator = (CannyViewAnimator) findViewById(R.id.choose_animator);
        inButton = (TextView) findViewById(R.id.choose_in_button);
        outButton = (TextView) findViewById(R.id.choose_out_button);
        checkContainer = (FrameLayout) findViewById(R.id.choose_container);
        typeCheck = (SwitchCompat) findViewById(R.id.choose_check);
        startButton = (TextView) findViewById(R.id.choose_start_button);
        locationTypeChooser = (RadioGroup) findViewById(R.id.choose_location_container);
    }

    private void initViews() {
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animator.setDisplayedChildIndex(animator.getDisplayedChildIndex() + 1);
            }
        });
        checkContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animator.setAnimateType(typeCheck.isChecked()
                        ? CannyViewAnimator.SEQUENTIALLY
                        : CannyViewAnimator.TOGETHER);
                typeCheck.setChecked(!typeCheck.isChecked());

            }
        });
        inButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(RequestResultCodes.REQUEST_IN);
            }
        });
        outButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(RequestResultCodes.REQUEST_OUT);
            }
        });
        locationTypeChooser.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.choose_location_for_position:
                        animator.setLocationType(CannyViewAnimator.FOR_POSITION);
                        break;
                    case R.id.choose_location_in_always_top:
                        animator.setLocationType(CannyViewAnimator.IN_ALWAYS_TOP);
                        break;
                    case R.id.choose_location_out_always_top:
                        animator.setLocationType(CannyViewAnimator.OUT_ALWAYS_TOP);
                        break;
                }
            }
        });
        locationTypeChooser.check(R.id.choose_location_for_position);
    }

    private void startActivityForResult(int code) {
        Intent intent = new Intent(this, ChooseListActivity.class);
        intent.putExtra(String.valueOf(RequestResultCodes.REQUEST), code);
        startActivityForResult(intent, RequestResultCodes.REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RequestResultCodes.RESULT_IN
                && resultCode != RequestResultCodes.RESULT_OUT) return;
        List<Integer> positionsAnimators = data
                .getIntegerArrayListExtra(RequestResultCodes.KEY_ANIMATORS);
        List<DefaultCannyAnimators> animators = new ArrayList<>(positionsAnimators.size());
        String text = "";
        for (Integer position : positionsAnimators) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                position = position + RevealAnimators.values().length;
            }
            DefaultCannyAnimators newAnimator = position < RevealAnimators.values().length
                    ? RevealAnimators.values()[position]
                    : PropertyAnimators.values()[position - RevealAnimators.values().length];
            animators.add(newAnimator);
            text += getNormalName(newAnimator.getName()) + " ";
        }
        if (resultCode == RequestResultCodes.RESULT_IN) {
            animator.setInAnimator(animators);
            inButton.setText(text);
        } else {
            animator.setOutAnimator(animators);
            outButton.setText(text);
        }
    }

    private String getNormalName(String name) {
        name = name.replaceAll("_", " ");
        return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
    }
}