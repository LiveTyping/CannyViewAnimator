package ru.ltst.cannyviewanimator.choose;

import android.view.View;
import android.widget.TextView;

import com.danil.recyclerbindableadapter.library.view.BindableViewHolder;

import ru.ltst.cannyviewanimator.R;
import ru.ltst.library.CannyViewAnimator;

public class ChooseItemViewHolder extends BindableViewHolder<ChooseModel,
        ChooseItemViewHolder.OnItemClick> {
    private final TextView mainText;


    public ChooseItemViewHolder(View itemView) {
        super(itemView);
        mainText = ((TextView) itemView.findViewById(R.id.main_item_text));
    }

    @Override
    public void bindView(int position, final ChooseModel item, final OnItemClick actionListener) {
        mainText.setText(getNormalName(item.getAnimators().getName()));
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (actionListener == null) return;
                if (item.getType() == ChooseModel.IN) {
                    actionListener.onInClick(item.getAnimators().getName(),
                            item.getAnimators().getInAnimator());
                } else if (item.getType() == ChooseModel.OUT) {
                    actionListener.onOutClick(item.getAnimators().getName(),
                            item.getAnimators().getOutAnimator());
                }
            }
        });
    }

    private String getNormalName(String name) {
        name = name.replaceAll("_", " ");
        return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
    }

    interface OnItemClick extends BindableViewHolder.ActionListener<ChooseModel> {
        void onInClick(String name, CannyViewAnimator.InAnimator inAnimator);

        void onOutClick(String name, CannyViewAnimator.OutAnimator outAnimator);
    }
}
