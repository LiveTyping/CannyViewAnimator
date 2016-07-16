package com.livetyping.cannyviewanimator.xml;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.livetyping.cannyviewanimator.R;
import com.livetyping.library.CannyViewAnimator;

public class XmlActivity extends AppCompatActivity {
    private CannyViewAnimator animator;
    private TextView startButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xml);
        findViews();
        initViews();
    }

    private void findViews(){
        animator = (CannyViewAnimator) findViewById(R.id.xml_animator);
        startButton = (TextView) findViewById(R.id.xml_start_button);
    }

    private void initViews(){
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animator.setDisplayedChildIndex(animator.getDisplayedChildIndex() + 1);
            }
        });
    }
}
