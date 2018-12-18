package cn.sighttechnology.armscomponent.commonres.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;
//解决viewpager嵌套recyclerview列表 高度不适配的问题
public class ThirdViewPager extends ViewPager {

    private int current;
    private int viewHeight = 0;

    private boolean scrollble = true;

    private int counter = 0;

    /**
     * 用于指示是否是第一次选中页面的标识位
     */
    private boolean isFirstSelected = true;

    public ThirdViewPager(Context context) {
        super(context);
    }

    public ThirdViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

//        Log.d("ZZF", "onMeasure ----------------------- "+(++counter));

//        View childView = getChildAt(getCurrentItem());


        //最大子项高度
        int maxChildHeight = 0;

        //遍历子项获取最大高度
        for (int i = 0; i < getChildCount(); i++) {
            getChildAt(i).measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            if (maxChildHeight < getChildAt(i).getMeasuredHeight()) {
                maxChildHeight = getChildAt(i).getMeasuredHeight();
            }
        }
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(maxChildHeight, MeasureSpec.EXACTLY);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

//        isFirstSelected = false;
//        return;

//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //==========================================================================
        //有可能没有子view
//        if (childView != null) {
//            childView.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
//            viewHeight = childView.getMeasuredHeight();
//            //得到父元素对自身设定的高
//            //UNSPECIFIED(未指定),父元素不对子元素施加任何束缚，子元素可以得到任意想要的大小
//            //EXACTLY(完全)，父元素决定自元素的确切大小，子元素将被限定在给定的边界里而忽略它本身大小；
//            //AT_MOST(至多)，子元素至多达到指定大小的值。
//            heightMeasureSpec = MeasureSpec.makeMeasureSpec(viewHeight, MeasureSpec.EXACTLY);
//        }
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void resetHeight(int current) {
        this.current = current;
        if (getChildCount() > current) {
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) getLayoutParams();
            if (layoutParams == null) {
                layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, viewHeight);
            } else {
                layoutParams.height = viewHeight;
            }
            setLayoutParams(layoutParams);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (!scrollble) {
            return true;
        }
        return super.onTouchEvent(ev);
    }

    public boolean isScrollble() {
        return scrollble;
    }

    public void setScrollble(boolean scrollble) {
        this.scrollble = scrollble;
    }

}
