package ru.ltst.cannyviewanimator;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.danil.recyclerbindableadapter.library.SimpleBindableAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ru.ltst.library.CannyViewAnimator;
import ru.ltst.library.DefaultCannyAnimators;
import ru.ltst.library.animators.property.PropertyAnimators;
import ru.ltst.library.animators.reveal.RevealAnimators;

public class MainActivity extends AppCompatActivity implements ItemViewHolder.OnItemClick {

    private CannyViewAnimator animator;
    private TextView inText, outText, startButton;
    private SwitchCompat typeCheck;
    private RecyclerView inRecycler, outRecycler;
    private FrameLayout checkContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        findViews();
        initViews();
    }

    private void findViews() {
        animator = (CannyViewAnimator) findViewById(R.id.main_animator);
        inText = (TextView) findViewById(R.id.main_in_text);
        outText = (TextView) findViewById(R.id.main_out_text);
        checkContainer = (FrameLayout) findViewById(R.id.check_container);
        typeCheck = (SwitchCompat) findViewById(R.id.main_check);
        inRecycler = (RecyclerView) findViewById(R.id.main_in_recycler);
        outRecycler = (RecyclerView) findViewById(R.id.main_out_recycler);
        startButton = (TextView) findViewById(R.id.main_start_button);
    }

    private void initViews() {
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animator.setDisplayedChild(animator.getDisplayedChild() + 1);
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
        inRecycler.setAdapter(getAdapter(MainModel.IN));
        inRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        outRecycler.setAdapter(getAdapter(MainModel.OUT));
        outRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
    }

    @Override
    public void onInClick(String name, CannyViewAnimator.InAnimator inAnimator) {
        animator.setInAnimator(inAnimator);
        inText.setText("Selected In " + getNormalName(name));
    }

    @Override
    public void onOutClick(String name, CannyViewAnimator.OutAnimator outAnimator) {
        animator.setOutAnimator(outAnimator);
        outText.setText("Selected Out " + getNormalName(name));
    }

    private String getNormalName(String name) {
        name = name.replaceAll("_", " ");
        return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
    }

    private SimpleBindableAdapter getAdapter(@MainModel.Type int type) {
        SimpleBindableAdapter<MainModel> adapter =
                new SimpleBindableAdapter<>(R.layout.main_item, ItemViewHolder.class);
        adapter.setActionListener(this);
        List<DefaultCannyAnimators> items = new ArrayList<>();
        items.addAll(Arrays.asList(PropertyAnimators.values()));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            items.addAll(Arrays.asList(RevealAnimators.values()));
        }
        adapter.addAll(MainModel.getAnimators(type, items));
        return adapter;
    }

    @Override
    public void OnItemClickListener(int position, MainModel item) {

    }
}
