package me.yostane.tutorial.daggertutorial

import android.app.Application
import android.util.Log
import dagger.Component
import java.util.logging.Logger
import javax.inject.Singleton


@Singleton @Component interface ApplicationComponent {
    fun injectIntoMainActivity(activity: MainActivity)
    fun getMaker(): CoffeeMaker
}

class DiTutorialApplication : Application() {
    // DaggerApplicationComponent is the implementation of ApplicationComponent provided by Dagger
    val appComponent = DaggerApplicationComponent.create()

    override fun onCreate() {
        super.onCreate()
        Log.d("DAGGER TUTO", "Brew: ${appComponent.getMaker().brew()}")
    }
}