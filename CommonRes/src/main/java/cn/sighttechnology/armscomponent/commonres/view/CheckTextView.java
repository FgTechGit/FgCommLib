package cn.sighttechnology.armscomponent.commonres.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

public class CheckTextView extends TextView {
    private boolean isCheck =   false;

    public CheckTextView(Context context) {
        super(context);
    }

    public CheckTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CheckTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CheckTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }
}
