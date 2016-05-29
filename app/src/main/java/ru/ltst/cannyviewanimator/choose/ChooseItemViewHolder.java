package ru.ltst.cannyviewanimator.choose;

import android.view.View;
import android.widget.TextView;

import com.danil.recyclerbindableadapter.library.view.BindableViewHolder;

import ru.ltst.cannyviewanimator.R;
import ru.ltst.library.CannyViewAnimator;
import ru.ltst.library.interfaces.InAnimator;
import ru.ltst.library.interfaces.OutAnimator;

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
                            item.getAnimators());
                } else if (item.getType() == ChooseModel.OUT) {
                    actionListener.onOutClick(item.getAnimators().getName(),
                            item.getAnimators());
                }
            }
        });
    }

    private String getNormalName(String name) {
        name = name.replaceAll("_", " ");
        return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
    }

    interface OnItemClick extends BindableViewHolder.ActionListener<ChooseModel> {
        void onInClick(String name, InAnimator inAnimator);

        void onOutClick(String name, OutAnimator outAnimator);
    }
}
