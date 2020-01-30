package me.yostane.tutorial.daggertutorial

import android.app.Application
import dagger.Component


@Component interface ApplicationComponent {
    fun inject(mainActivity: MainActivity)
}

class DiTutorialApplication : Application() {

    // DaggerApplicationComponent is the implementation of ApplicationComponent provided by Dagger
    val appComponent = DaggerApplicationComponent.create()
}