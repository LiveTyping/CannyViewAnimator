package com.livetyping.cannyviewanimator.choose.list;

import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.danil.recyclerbindableadapter.library.view.BindableViewHolder;

import com.livetyping.cannyviewanimator.R;

public class ChooseItemViewHolder extends BindableViewHolder<ChooseModel,
        ChooseItemViewHolder.OnItemClick> {
    private final CardView container;
    private final TextView mainText;
    private final ImageView check;

    public ChooseItemViewHolder(View itemView) {
        super(itemView);
        container = (CardView) itemView.findViewById(R.id.choose_item_container);
        mainText = ((TextView) itemView.findViewById(R.id.choose_item_text));
        check = (ImageView) itemView.findViewById(R.id.choose_item_check);
    }

    @Override
    public void bindView(int position, ChooseModel item, OnItemClick actionListener) {
        super.bindView(position, item, actionListener);
        mainText.setText(getNormalName(item.getAnimators().getName()));
        check.setVisibility(item.isChecked() ? View.VISIBLE : View.GONE);
    }

    private String getNormalName(String name) {
        name = name.replaceAll("_", " ");
        return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
    }

    interface OnItemClick extends BindableViewHolder.ActionListener<ChooseModel> {
    }
}
