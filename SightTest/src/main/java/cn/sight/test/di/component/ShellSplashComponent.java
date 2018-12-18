package cn.sight.test.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import cn.sight.test.di.module.ShellSplashModule;

import com.jess.arms.di.scope.ActivityScope;

import cn.sight.test.mvp.ui.activity.ShellSplashActivity;

@ActivityScope
@Component(modules = ShellSplashModule.class, dependencies = AppComponent.class)
public interface ShellSplashComponent {
    void inject(ShellSplashActivity activity);
}