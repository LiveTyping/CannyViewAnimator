package ru.ltst.library;

/**
 * Created by Danil on 09.05.2016.
 */
public interface DefaultCannyAnimators {
    String getName();
    CannyViewAnimator.InAnimator getInAnimator();
    CannyViewAnimator.OutAnimator getOutAnimator();
}
