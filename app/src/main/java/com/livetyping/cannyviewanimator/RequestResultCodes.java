package com.livetyping.cannyviewanimator;

/**
 * Created by Danil on 23.06.2016.
 */
public class RequestResultCodes {
    // ChooseActivity -> ChooseListActivity
    public static final int REQUEST = 11010;
    public static final int REQUEST_IN = 11011;
    public static final int REQUEST_OUT = 11012;

    //ChooseListActivity -> ChooseActivity
    public static final int RESULT_IN = 11260;
    public static final int RESULT_OUT = 11261;
    public static final String KEY_ANIMATORS = "ChooseListActivity.checked.animators";
}
