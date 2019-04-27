package me.yostane.tutorial.daggertutorial.ui

import android.app.Activity
import dagger.android.AndroidInjector
import dagger.android.ActivityKey
import dagger.multibindings.IntoMap
import dagger.Binds
import dagger.Module


@Module(subcomponents = [MainActivityComponent::class])
abstract class MainActivityModule {

    @Binds
    @IntoMap
    @ActivityKey(MainActivity::class)
    internal abstract fun bindMainActivityInjectorFactory(builder: MainActivityComponent.Builder): AndroidInjector.Factory<out Activity>
}