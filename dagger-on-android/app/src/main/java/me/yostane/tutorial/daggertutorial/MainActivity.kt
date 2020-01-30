package me.yostane.tutorial.daggertutorial

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import javax.inject.Inject

// Simple class that simulates a view model
class MainViewModel @Inject constructor() {
    fun greet() = "hello"
}

class MainActivity : AppCompatActivity() {

    @Inject lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (applicationContext as DiTutorialApplication).appComponent.inject(this)

        setContentView(R.layout.activity_main)
        Log.d("Test", viewModel.greet())
    }
}
