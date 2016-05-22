package ru.ltst.cannyviewanimator.xml;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import ru.ltst.cannyviewanimator.R;
import ru.ltst.library.CannyViewAnimator;

public class XmlActivity extends AppCompatActivity {
    private CannyViewAnimator animator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xml);
        animator = (CannyViewAnimator) findViewById(R.id.xml_animator);
        TextView startButton = (TextView) findViewById(R.id.xml_start_button);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animator.setDisplayedChild(animator.getDisplayedChild() + 1);
            }
        });
    }
}
