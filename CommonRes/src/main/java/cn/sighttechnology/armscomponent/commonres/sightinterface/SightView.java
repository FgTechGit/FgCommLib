package cn.sighttechnology.armscomponent.commonres.sightinterface;

import android.app.Activity;

import com.jess.arms.mvp.IView;

public interface SightView extends IView {
    Activity getSightActivity();//获取顶层的activity
}
