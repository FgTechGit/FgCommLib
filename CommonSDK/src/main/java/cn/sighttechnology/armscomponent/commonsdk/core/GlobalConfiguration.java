/*
 * Copyright 2018 JessYan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.sighttechnology.armscomponent.commonsdk.core;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.os.Debug;
import android.support.annotation.NonNull;
import android.support.multidex.MultiDex;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.DisplayMetrics;
import android.util.Log;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.Utils;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.jess.arms.base.delegate.AppLifecycles;
import com.jess.arms.di.module.ClientModule;
import com.jess.arms.di.module.GlobalConfigModule;
import com.jess.arms.http.log.RequestInterceptor;
import com.jess.arms.integration.ConfigModule;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
//import com.squareup.leakcanary.LeakCanary;
//import com.squareup.leakcanary.RefWatcher;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.socialize.PlatformConfig;
import com.uuch.adlibrary.utils.DisplayUtil;

import org.android.agoo.huawei.HuaWeiRegister;
import org.android.agoo.xiaomi.MiPushRegistar;

import java.util.List;

import butterknife.ButterKnife;
import cn.sighttechnology.armscomponent.commonsdk.BuildConfig;
import cn.sighttechnology.armscomponent.commonsdk.R;
import cn.sighttechnology.armscomponent.commonsdk.http.Api;
import cn.sighttechnology.armscomponent.commonsdk.http.SSLSocketClient;
import cn.sighttechnology.armscomponent.commonsdk.imgaEngine.Strategy.CommonGlideImageLoaderStrategy;
import cn.sighttechnology.armscomponent.commonsdk.BuildConfig;
import cn.sighttechnology.armscomponent.commonsdk.http.Api;
import cn.sighttechnology.armscomponent.commonsdk.http.SSLSocketClient;
import cn.sighttechnology.armscomponent.commonsdk.imgaEngine.Strategy.CommonGlideImageLoaderStrategy;
import cn.sighttechnology.armscomponent.commonsdk.utils.IntegerTypeAdapter;
import cn.sighttechnology.armscomponent.commonsdk.utils.LongTypeAdapter;
import me.jessyan.retrofiturlmanager.RetrofitUrlManager;
import okhttp3.OkHttpClient;
import timber.log.Timber;


/**
 * ================================================
 * CommonSDK 的 GlobalConfiguration 含有有每个组件都可公用的配置信息, 每个组件的 AndroidManifest 都应该声明此 ConfigModule
 *
 * @see <a href="https://github.com/JessYanCoding/ArmsComponent/wiki#3.3">ConfigModule wiki 官方文档</a>
 * Created by JessYan on 30/03/2018 17:16
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public class GlobalConfiguration implements ConfigModule {

    public static Context appContext;

    @Override
    public void applyOptions(Context context, GlobalConfigModule.Builder builder) {
        if (!BuildConfig.LOG_DEBUG) //Release 时,让框架不再打印 Http 请求和响应的信息
        {
            builder.printHttpLogLevel(RequestInterceptor.Level.NONE);
        }

        builder.baseurl(Api.APP_DOMAIN)
                //这地方的GlobalHttpHandlerImpl 需要注调 否则会覆盖其他module自定义的GlobalHttpHandler
//                .imageLoaderStrategy(new CommonGlideImageLoaderStrategy())
//                .globalHttpHandler(new GlobalHttpHandlerImpl(context))
                .responseErrorListener(new ResponseErrorListenerImpl())
                .gsonConfiguration((context1, gsonBuilder) -> {//这里可以自己自定义配置Gson的参数
                    gsonBuilder
                    .serializeNulls()//支持序列化null的参数
                    .enableComplexMapKeySerialization()
                    .registerTypeAdapter(Integer.class, new IntegerTypeAdapter())
                    .registerTypeAdapter(Long.class, new LongTypeAdapter())
                    ;//支持将序列化key为object的map,默认只能序列化key为string的map
                })
                .okhttpConfiguration(new ClientModule.OkhttpConfiguration() {
                    @Override
                    public void configOkhttp(Context context, OkHttpClient.Builder builder) {
                        builder.sslSocketFactory(SSLSocketClient.getSSLSocketFactory(), SSLSocketClient.getTrustManager());
                        builder.hostnameVerifier(SSLSocketClient.getHostnameVerifier());
                        //让 Retrofit 同时支持多个 BaseUrl 以及动态改变 BaseUrl. 详细使用请方法查看 https://github.com/JessYanCoding/RetrofitUrlManager
                        RetrofitUrlManager.getInstance().with(builder);
                    }
                })
                .rxCacheConfiguration((context1, rxCacheBuilder) -> {//这里可以自己自定义配置RxCache的参数
                    rxCacheBuilder.useExpiredDataIfLoaderNotAvailable(true);
                    return null;
                });
    }

    @Override
    public void injectAppLifecycle(Context context, List<AppLifecycles> lifecycles) {
        // AppDelegate.Lifecycle 的所有方法都会在基类Application对应的生命周期中被调用,所以在对应的方法中可以扩展一些自己需要的逻辑
        lifecycles.add(new AppLifecycles() {
            //            private RefWatcher mRefWatcher;//leakCanary观察器
            @Override
            public void attachBaseContext(@NonNull Context base) {
                MultiDex.install(base);
            }

            @Override
            public void onCreate(@NonNull Application application) {

                appContext = application;

                //初始化AppUtils
                Utils.init(application);
                if (BuildConfig.LOG_DEBUG) {//Timber日志打印
                    Timber.plant(new Timber.DebugTree());
                    ButterKnife.setDebug(true);
                    ARouter.openLog();     // 打印日志
                    ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
                    RetrofitUrlManager.getInstance().setDebug(true);
                }
                ARouter.init(application); // 尽可能早,推荐在Application中初始化

                //设置提示信息
                ClassicsHeader.REFRESH_HEADER_PULLING = "下拉可以刷新";
                ClassicsHeader.REFRESH_HEADER_REFRESHING = "正在刷新...";
                ClassicsHeader.REFRESH_HEADER_LOADING = "正在加载...";
                ClassicsHeader.REFRESH_HEADER_RELEASE = "释放立即刷新";
                ClassicsHeader.REFRESH_HEADER_FINISH = "刷新完成";
                ClassicsHeader.REFRESH_HEADER_FAILED = "刷新失败";

                ClassicsFooter.REFRESH_FOOTER_PULLING = "上拉加载更多";
                ClassicsFooter.REFRESH_FOOTER_RELEASE = "释放立即加载";
                ClassicsFooter.REFRESH_FOOTER_LOADING = "正在加载...";
                ClassicsFooter.REFRESH_FOOTER_REFRESHING = "正在刷新..";
                ClassicsFooter.REFRESH_FOOTER_FINISH = "加载完成";
                ClassicsFooter.REFRESH_FOOTER_FAILED = "加载失败";
                ClassicsFooter.REFRESH_FOOTER_NOTHING = "没有更多数据了";

                //设置全局的Header构建器
                SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
                    @Override
                    public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                        layout.setPrimaryColorsId(android.R.color.white, android.R.color.black);//全局设置主题颜色 前一个是背景色，后一个是字体颜色
                        ClassicsHeader customHeader = new ClassicsHeader(context);
                        return customHeader;//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
                    }
                });
                //设置全局的Footer构建器
                SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
                    @Override
                    public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                        ClassicsFooter customFooter = new ClassicsFooter(context);
                        //指定为经典Footer，默认是 BallPulseFooter
                        return customFooter;
                    }
                });

                // 首页活动的弹窗初始化
                Fresco.initialize(context);
                DisplayMetrics dm = context.getResources().getDisplayMetrics();
                DisplayUtil.density = dm.density;
                DisplayUtil.densityDPI = dm.densityDpi;
                DisplayUtil.screenWidthPx = dm.widthPixels;
                DisplayUtil.screenhightPx = dm.heightPixels;
                DisplayUtil.screenWidthDip = DisplayUtil.px2dip(context.getApplicationContext(), dm.widthPixels);
                DisplayUtil.screenHightDip = DisplayUtil.px2dip(context.getApplicationContext(), dm.heightPixels);
            }

            @Override
            public void onTerminate(@NonNull Application application) {
//                this.mRefWatcher = null;
            }
        });
    }

    @Override
    public void injectActivityLifecycle(Context context, List<Application.ActivityLifecycleCallbacks> lifecycles) {
        lifecycles.add(new ActivityLifecycleCallbacksImpl());
    }

    @Override
    public void injectFragmentLifecycle(Context context, List<FragmentManager.FragmentLifecycleCallbacks> lifecycles) {
        lifecycles.add(new FragmentManager.FragmentLifecycleCallbacks() {

            @Override
            public void onFragmentCreated(FragmentManager fm, Fragment f, Bundle savedInstanceState) {
                // 在配置变化的时候将这个 Fragment 保存下来,在 Activity 由于配置变化重建是重复利用已经创建的Fragment。
                // https://developer.android.com/reference/android/app/Fragment.html?hl=zh-cn#setRetainInstance(boolean)
                // 在 Activity 中绑定少量的 Fragment 建议这样做,如果需要绑定较多的 Fragment 不建议设置此参数,如 ViewPager 需要展示较多 Fragment
                f.setRetainInstance(true);
            }
        });
    }
}
