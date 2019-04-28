package com.example.dagger.kotlin

import dagger.Reusable
import javax.inject.Inject
import javax.inject.Singleton

@Reusable
class ElectricHeater @Inject constructor() {
    var heating: Boolean = false
    init {
        println("++++Electric heater instantiated")
    }
    val isHot get() = heating
    fun on() {
        println("Electric heater on")
        this.heating = true
    }
    fun off() {
        println("Electric heater off")
        this.heating = false
    }
}

@Reusable
class Thermosiphon @Inject constructor(private val heater: ElectricHeater) {

    var name = ""

    init {
        println("++++Thermosiphon $name instantiated")
    }
    fun pump() {
        println("Thermosiphon $name is pumping")
        if (heater.isHot) {
            println("Heater is hot !!!")
        }
    }
}

class CoffeeMaker @Inject constructor() {
    @Inject
    lateinit var heater: ElectricHeater
    @Inject
    lateinit var pump: Thermosiphon
    init {
        println("++++CoffeeMaker instantiated")
    }
    fun brew() {
        println("Brewing coffee")
        heater.on()
        pump.pump()
        println("Coffee ready :)")
        heater.off()
    }
}
