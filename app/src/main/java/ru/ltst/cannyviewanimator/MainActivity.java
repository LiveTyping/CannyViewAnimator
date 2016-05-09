package ru.ltst.cannyviewanimator;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.TextView;

import com.danil.recyclerbindableadapter.library.SimpleBindableAdapter;

import java.util.Arrays;

import ru.ltst.library.CannyViewAnimator;
import ru.ltst.library.DefaultCannyAnimators;
import ru.ltst.library.DefaultInAnimators;
import ru.ltst.library.DefaultOutAnimators;

public class MainActivity extends AppCompatActivity implements ItemViewHolder.OnItemClick {

    private CannyViewAnimator animator;
    private TextView inText, outText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        initViews();
    }

    private void initViews() {
        animator = (CannyViewAnimator) findViewById(R.id.main_animator);
        animator.setAnimateType(CannyViewAnimator.TOGETHER);
        inText = (TextView) findViewById(R.id.main_in_text);
        outText = (TextView) findViewById(R.id.main_out_text);
        findViewById(R.id.main_start_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animator.setDisplayedChild(animator.getDisplayedChild() + 1);
            }
        });

        SimpleBindableAdapter<DefaultCannyAnimators> inAdapter =
                new SimpleBindableAdapter<>(R.layout.main_item, ItemViewHolder.class);
        inAdapter.setActionListener(this);
        inAdapter.addAll(Arrays.asList(DefaultInAnimators.values()));
        RecyclerView inRecycler = (RecyclerView) findViewById(R.id.main_in_recycler);
        inRecycler.setAdapter(inAdapter);
        inRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        SimpleBindableAdapter<DefaultCannyAnimators> outAdapter =
                new SimpleBindableAdapter<>(R.layout.main_item, ItemViewHolder.class);
        outAdapter.setActionListener(this);
        outAdapter.addAll(Arrays.asList(DefaultOutAnimators.values()));
        RecyclerView outRecycler = (RecyclerView) findViewById(R.id.main_out_recycler);
        outRecycler.setAdapter(outAdapter);
        outRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
    }

    @Override
    public void onInClick(String name, CannyViewAnimator.InAnimator inAnimator) {
        animator.setInAnimator(inAnimator);
        inText.setText("Selected " + getNormalName(name));
    }

    @Override
    public void onOutClick(String name, CannyViewAnimator.OutAnimator outAnimator) {
        animator.setOutAnimator(outAnimator);
        outText.setText("Selected " + getNormalName(name));
    }

    private String getNormalName(String name) {
        name = name.replaceAll("_", " ");
        return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
    }

    @Override
    public void OnItemClickListener(int position, DefaultCannyAnimators item) {

    }
}
