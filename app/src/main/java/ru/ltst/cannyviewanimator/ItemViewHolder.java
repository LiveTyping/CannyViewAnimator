package ru.ltst.cannyviewanimator;

import android.view.View;
import android.widget.TextView;

import com.danil.recyclerbindableadapter.library.view.BindableViewHolder;

import ru.ltst.library.CannyViewAnimator;
import ru.ltst.library.DefaultCannyAnimators;
import ru.ltst.library.DefaultInAnimators;

public class ItemViewHolder extends BindableViewHolder<DefaultCannyAnimators,
        ItemViewHolder.OnItemClick> {

    public ItemViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void bindView(int position, final DefaultCannyAnimators item, final OnItemClick actionListener) {
        ((TextView) itemView.findViewById(R.id.main_item_text)).setText(getNormalName(item.getName()));
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (actionListener == null) return;
                if (item.getInAnimator() != null) {
                    actionListener.onInClick(item.getName(), item.getInAnimator());
                } else if (item.getOutAnimator() != null) {
                    actionListener.onOutClick(item.getName(), item.getOutAnimator());
                }
            }
        });
    }

    private String getNormalName(String name) {
        name = name.replaceAll("_", " ");
        return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
    }

    interface OnItemClick extends BindableViewHolder.ActionListener<DefaultCannyAnimators> {
        void onInClick(String name, CannyViewAnimator.InAnimator inAnimator);

        void onOutClick(String name, CannyViewAnimator.OutAnimator outAnimator);
    }
}
