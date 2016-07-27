package com.livetyping.cannyviewanimator.choose.list;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.danil.recyclerbindableadapter.library.RecyclerBindableAdapter;
import com.danil.recyclerbindableadapter.library.SimpleBindableAdapter;
import com.livetyping.cannyviewanimator.R;
import com.livetyping.cannyviewanimator.RequestResultCodes;
import com.livetyping.library.animators.property.PropertyAnimators;
import com.livetyping.library.animators.reveal.RevealAnimators;
import com.livetyping.library.interfaces.DefaultCannyAnimators;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChooseListActivity extends AppCompatActivity implements ChooseItemViewHolder.OnItemClick, Toolbar.OnMenuItemClickListener {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private SimpleBindableAdapter<ChooseModel> adapter;
    private ArrayList<Integer> checked = new ArrayList<>();
    private boolean isIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_list_activity);
        int code = getIntent().getIntExtra(String.valueOf(RequestResultCodes.REQUEST), 0);
        isIn = code == RequestResultCodes.REQUEST_IN;
        findViews();
        initViews();
    }

    private void findViews() {
        recyclerView = (RecyclerView) findViewById(R.id.choose_list_recycler);
        toolbar = (Toolbar) findViewById(R.id.choose_list_toolbar);
    }

    private void initViews() {
        recyclerView.setAdapter(getAdapter());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        toolbar.inflateMenu(R.menu.choose_list);
        toolbar.setOnMenuItemClickListener(this);
    }

    private SimpleBindableAdapter getAdapter() {
        adapter = new SimpleBindableAdapter<>(R.layout.choose_item, ChooseItemViewHolder.class);
        adapter.setActionListener(this);
        List<DefaultCannyAnimators> items = new ArrayList<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            items.addAll(Arrays.asList(RevealAnimators.values()));
        }
        items.addAll(Arrays.asList(PropertyAnimators.values()));
        adapter.addAll(ChooseModel.getAnimators(items));
        return adapter;
    }

    @Override
    public void OnItemClickListener(int position, ChooseModel item) {
        if (item.isChecked()) {
            checked.remove(checked.indexOf(adapter.indexOf(item)));
        } else {
            checked.add(adapter.indexOf(item));
        }
        item.setChecked(!item.isChecked());
        adapter.notifyItemChanged(position);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.choose_list_apply:
                Intent intent = new Intent();
                if (checked.size() == 0) checked.add(0);
                intent.putExtra(RequestResultCodes.KEY_ANIMATORS, checked);
                int code = isIn ? RequestResultCodes.RESULT_IN : RequestResultCodes.RESULT_OUT;
                setResult(code, intent);
                onBackPressed();
                break;
        }
        return false;
    }
}
