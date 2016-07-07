package com.livetyping.library;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.IdRes;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

public class ViewAnimator extends FrameLayout {
    private static final String KEY_LAST_WHICH_INDEX = "key.last.which.index";

    protected int lastWhichIndex = 0;

    public ViewAnimator(Context context) {
        super(context);
    }

    public ViewAnimator(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setDisplayedChildId(@IdRes int id) {
        if (getDisplayedChildId() == id) {
            return;
        }
        for (int i = 0, count = getChildCount(); i < count; i++) {
            if (getChildAt(i).getId() == id) {
                setDisplayedChild(i);
                return;
            }
        }
        throw new IllegalArgumentException("No view with ID " + id);
    }

    public void setDisplayedChild(int inChildIndex) {
        if (inChildIndex >= getChildCount()) {
            inChildIndex = 0;
        } else if (inChildIndex < 0) {
            inChildIndex = getChildCount() - 1;
        }
        boolean hasFocus = getFocusedChild() != null;
        changeVisibility(getChildAt(inChildIndex), getChildAt(lastWhichIndex));
        lastWhichIndex = inChildIndex;
        if (hasFocus) {
            requestFocus(FOCUS_FORWARD);
        }
    }

    protected void changeVisibility(View inChild, View outChild) {
        outChild.setVisibility(INVISIBLE);
        inChild.setVisibility(VISIBLE);
    }

    public int getDisplayedChildId() {
        return getChildAt(getDisplayedChild()).getId();
    }

    public int getDisplayedChild() {
        return lastWhichIndex;
    }

    public View getCurrentView() {
        return getChildAt(lastWhichIndex);
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        Log.d("ViewAnimator", "addView");
        super.addView(child, index, params);
        if (getChildCount() == 1) {
            child.setVisibility(View.VISIBLE);
        } else {
            child.setVisibility(View.INVISIBLE);
        }
        if (index >= 0 && lastWhichIndex >= index) {
            setDisplayedChild(lastWhichIndex + 1);
        }
    }

    @Override
    public void removeAllViews() {
        super.removeAllViews();
        lastWhichIndex = 0;
    }

    @Override
    public void removeView(View view) {
        final int index = indexOfChild(view);
        if (index >= 0) {
            removeViewAt(index);
        }
    }

    @Override
    public void removeViewAt(int index) {
        super.removeViewAt(index);
        final int childCount = getChildCount();
        if (childCount == 0) {
            lastWhichIndex = 0;
        } else if (lastWhichIndex >= childCount) {
            setDisplayedChild(childCount - 1);
        } else if (lastWhichIndex == index) {
            setDisplayedChild(lastWhichIndex);
        }
    }

    @Override
    public void removeViewInLayout(View view) {
        removeView(view);
    }

    @Override
    public void removeViews(int start, int count) {
        super.removeViews(start, count);
        if (getChildCount() == 0) {
            lastWhichIndex = 0;
        } else if (lastWhichIndex >= start && lastWhichIndex < start + count) {
            setDisplayedChild(lastWhichIndex);
        }
    }

    @Override
    public void removeViewsInLayout(int start, int count) {
        removeViews(start, count);
    }


    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (!(state instanceof SavedState)) {
            Log.d("ViewAnimator", "onRestoreInstanceState super");
            super.onRestoreInstanceState(state);
            return;
        }
        Log.d("ViewAnimator", "onRestoreInstanceState SavedState");
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        lastWhichIndex = ss.lastWhichIndex;
        setDisplayedChild(lastWhichIndex);

    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Log.d("ViewAnimator", "onSaveInstanceState");
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.lastWhichIndex = lastWhichIndex;
        return savedState;
    }

    public static class SavedState extends View.BaseSavedState {
        int lastWhichIndex;

        SavedState(Parcelable superState) {
            super(superState);
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeInt(this.lastWhichIndex);
        }

        @Override
        public String toString() {
            return "ViewAnimator.SavedState{" +
                    "lastWhichIndex=" + lastWhichIndex +
                    '}';
        }

        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() {
            @Override
            public SavedState createFromParcel(Parcel source) {
                return new SavedState(source);
            }

            @Override
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };

        protected SavedState(Parcel in) {
            super(in);
            this.lastWhichIndex = in.readInt();
        }

    }

}
