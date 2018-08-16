package me.yostane.tutorial.daggertutorial.di

import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import me.yostane.tutorial.daggertutorial.DaggerTurorialApplication
import javax.inject.Singleton
import me.yostane.tutorial.daggertutorial.ui.MainActivityModule


@Singleton
@Component(modules = arrayOf(AndroidSupportInjectionModule::class,
        AppModule::class,
        MainActivityModule::class))
interface AppComponent {

    fun inject(app: DaggerTurorialApplication)
}