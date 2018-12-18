package cn.sighttechnology.armscomponent.commonres.view;

import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.PopupWindow;

/**
 * 解决7系列系统 showAsDropDown() 位置不正确的问题的自定义PopupWindow
 */
public class CustomPopupWindow extends PopupWindow {

    public CustomPopupWindow(Context context) {
        super(context);
    }

    public CustomPopupWindow(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomPopupWindow(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CustomPopupWindow(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public CustomPopupWindow() {
        super();
    }

    public CustomPopupWindow(View contentView) {
        super(contentView);
    }

    public CustomPopupWindow(int width, int height) {
        super(width, height);
    }

    public CustomPopupWindow(View contentView, int width, int height) {
        super(contentView, width, height);
    }

    public CustomPopupWindow(View contentView, int width, int height, boolean focusable) {
        super(contentView, width, height, focusable);
    }

    @Override
    public void showAsDropDown(View anchor) {

        //对 7.0 和 7.1.1 单独处理
        if (Build.VERSION.SDK_INT == 24 || Build.VERSION.SDK_INT == 25) {
            Rect rect = new Rect();
            anchor.getGlobalVisibleRect(rect);
            int h = anchor.getResources().getDisplayMetrics().heightPixels - rect.bottom;
            setHeight(h);
        }
        super.showAsDropDown(anchor);
    }

}
