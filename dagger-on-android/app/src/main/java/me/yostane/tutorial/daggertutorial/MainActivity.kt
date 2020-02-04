package me.yostane.tutorial.daggertutorial

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject lateinit var coffeeMaker: CoffeeMaker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (applicationContext as DiTutorialApplication).appComponent.injectIntoMainActivity(this)
        Log.d("Test", coffeeMaker.brew())
        setContentView(R.layout.activity_main)
        this.label.text = this.coffeeMaker.brew()
    }
}
