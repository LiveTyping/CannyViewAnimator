package ru.ltst.cannyviewanimator;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

import ru.ltst.library.DefaultCannyAnimators;

/**
 * Created by Danil on 15.05.2016.
 */
public class MainModel {
    public static final int IN = 0;
    public static final int OUT = 1;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({IN, OUT})
    @interface Type {
    }

    @Type
    private int type;
    private DefaultCannyAnimators animators;

    public MainModel(@Type int type, DefaultCannyAnimators animators) {
        this.type = type;
        this.animators = animators;
    }

    public int getType() {
        return type;
    }

    public DefaultCannyAnimators getAnimators() {
        return animators;
    }

    public static List<MainModel> getAnimators(@Type int type,
                                               List<? extends DefaultCannyAnimators> animatorsList) {
        ArrayList<MainModel> mainModels = new ArrayList<>(animatorsList.size());
        for (DefaultCannyAnimators animators : animatorsList) {
            mainModels.add(new MainModel(type, animators));
        }
        return mainModels;
    }
}
