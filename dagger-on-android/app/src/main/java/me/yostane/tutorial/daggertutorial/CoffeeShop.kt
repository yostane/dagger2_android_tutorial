package me.yostane.tutorial.daggertutorial

import dagger.Component
import javax.inject.Inject
import javax.inject.Singleton

@Singleton class ElectricHeater @Inject constructor() {
    var heating: Boolean = false
    val isHot get() = heating
    fun on() { this.heating = true }
    fun off() { this.heating = false }
}

class Thermosiphon @Inject constructor( private val heater: ElectricHeater ) {
    fun pump() {    }
}

class CoffeeMaker @Inject constructor() {
    @Inject lateinit var heater: ElectricHeater
    @Inject lateinit var pump: Thermosiphon

    fun brew(): String {
        heater.on()
        pump.pump()
        heater.off()
        return "Success"
    }
}