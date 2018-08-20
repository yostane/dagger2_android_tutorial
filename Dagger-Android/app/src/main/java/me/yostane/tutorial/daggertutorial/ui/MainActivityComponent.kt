package me.yostane.tutorial.daggertutorial.ui

import me.yostane.tutorial.daggertutorial.ui.MainActivity
import dagger.android.AndroidInjector
import dagger.Subcomponent


@Subcomponent
interface MainActivityComponent : AndroidInjector<MainActivity> {
    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<MainActivity>()
}