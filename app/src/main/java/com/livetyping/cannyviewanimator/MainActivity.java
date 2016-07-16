package com.livetyping.cannyviewanimator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.livetyping.cannyviewanimator.choose.ChooseActivity;
import com.livetyping.cannyviewanimator.transition.TransitionActivity;
import com.livetyping.cannyviewanimator.xml.XmlActivity;

public class MainActivity extends AppCompatActivity {
    private View startXml;
    private View startTransition;
    private View startChoose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        initViews();
    }

    private void findViews() {
        startXml = findViewById(R.id.main_activity_start_xml);
        startTransition = findViewById(R.id.main_activity_start_transition);
        startChoose = findViewById(R.id.main_activity_start_choose);
    }

    private void initViews() {
        startXml.setOnClickListener(getStartActivityCL(XmlActivity.class));
        startTransition.setOnClickListener(getStartActivityCL(TransitionActivity.class));
        startChoose.setOnClickListener(getStartActivityCL(ChooseActivity.class));
    }

    private View.OnClickListener getStartActivityCL(final Class<?> cls) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, cls));
            }
        };
    }
}
