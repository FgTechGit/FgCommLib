package cn.sight.test.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import cn.sight.test.mvp.contract.ShellSplashContract;
import cn.sight.test.mvp.model.ShellSplashModel;


@Module
public class ShellSplashModule {
    private ShellSplashContract.View view;

    /**
     * 构建ShellSplashModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public ShellSplashModule(ShellSplashContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    ShellSplashContract.View provideShellSplashView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    ShellSplashContract.Model provideShellSplashModel(ShellSplashModel model) {
        return model;
    }
}