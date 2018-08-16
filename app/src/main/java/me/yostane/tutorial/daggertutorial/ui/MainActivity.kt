package me.yostane.tutorial.daggertutorial.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import dagger.android.AndroidInjection
import me.yostane.tutorial.daggertutorial.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
