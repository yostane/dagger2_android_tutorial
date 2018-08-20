package me.yostane.tutorial.daggertutorial.ui

import android.arch.lifecycle.ViewModel
import javax.inject.Inject


class MainViewModel @Inject constructor(): ViewModel() {
    fun greet() = "hello"
}