package com.livetyping.cannyviewanimator.choose.list;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

import com.livetyping.library.interfaces.DefaultCannyAnimators;

/**
 * Created by Danil on 15.05.2016.
 */
public class ChooseModel {
    private DefaultCannyAnimators animators;
    private boolean checked;

    public ChooseModel(DefaultCannyAnimators animators) {
        this.animators = animators;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public DefaultCannyAnimators getAnimators() {
        return animators;
    }

    public static List<ChooseModel> getAnimators(List<? extends DefaultCannyAnimators> animatorsList) {
        ArrayList<ChooseModel> chooseModels = new ArrayList<>(animatorsList.size());
        for (DefaultCannyAnimators animators : animatorsList) {
            chooseModels.add(new ChooseModel(animators));
        }
        return chooseModels;
    }
}
