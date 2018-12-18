package cn.sight.test.mvp.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.AppUtils;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.glide.GlideArms;
import com.jess.arms.utils.ArmsUtils;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.ArrayList;
import java.util.List;

import cn.sight.test.R;
import cn.sight.test.di.component.DaggerShellSplashComponent;
import cn.sight.test.di.module.ShellSplashModule;
import cn.sight.test.mvp.contract.ShellSplashContract;
import cn.sight.test.mvp.presenter.ShellSplashPresenter;
import cn.sighttechnology.armscomponent.commonres.utils.SightUtils;
import cn.sighttechnology.armscomponent.commonsdk.base.SightBaseActivity;
import me.relex.circleindicator.CircleIndicator;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * Description:
 * Data：2018-9-17 11:47:19
 * Author: yangjichao
 */
public class ShellSplashActivity extends SightBaseActivity<ShellSplashPresenter> implements ShellSplashContract.View {
    private ViewPager viewPager;
    private List<ImageView> imageViews;
    private boolean isFirstStart = false;
    private KProgressHUD hud;
    CircleIndicator indicator;
    FrameLayout frameLayout;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerShellSplashComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .shellSplashModule(new ShellSplashModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_shell_splash; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        viewPager   =   findViewById(R.id.view_pager);
        frameLayout =   findViewById(R.id.fl_in);
        imageViews  =   new ArrayList<>();
        indicator = (CircleIndicator) findViewById(R.id.indicator);

    }

    @Override
    public void showLoading() {
        hud.show();
    }

    @Override
    public void hideLoading() {
        hud.dismiss();
    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }

    @Override
    public Activity getSightActivity() {
        return this;
    }

}
