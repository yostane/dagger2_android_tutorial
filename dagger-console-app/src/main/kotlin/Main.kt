package com.example.dagger.kotlin

import dagger.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton class ElectricHeater @Inject constructor() {
    var heating: Boolean = false
    init {
        println("++++Electric header instantiated")
    }
    val isHot get() = heating
    fun on() {
        println("Electric header on")
        this.heating = true
    }
    fun off() {
        println("Electric header off")
        this.heating = false
    }
}

class Thermosiphon @Inject constructor( private val heater: ElectricHeater ) {
    init {
        println("++++Thermosiphon instantiated")
    }
    fun pump() {
        println("Thermosiphon is pumping")
        if (heater.isHot) {
            println("Heater is hot !!!")
        }
    }
}

class CoffeeMaker @Inject constructor() {
    @Inject lateinit var heater: ElectricHeater
    @Inject lateinit var pump: Thermosiphon
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

@Singleton @Component() interface CoffeeShop {
    fun maker(): CoffeeMaker
}

fun main(args: Array<String>) {
    val coffee = DaggerCoffeeShop.builder().build()
    coffee.maker().brew()
}
