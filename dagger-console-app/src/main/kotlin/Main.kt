package com.example.dagger.kotlin

import dagger.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ElectricHeater @Inject constructor() {

    init {
        println("++++Electric header instantiated")
    }

    var heating: Boolean = false

    fun on() {
        println("Electric header on")
        this.heating = true
    }

    fun off() {
        println("Electric header off")
        this.heating = false
    }

    val isHot get() = heating
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

class CoffeeMaker @Inject constructor(private val heater: ElectricHeater, private val pump: Thermosiphon) {

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

@Singleton
@Component()
interface CoffeeShop {
    fun maker(): CoffeeMaker
}

fun main(args: Array<String>) {
    val coffee = DaggerCoffeeShop.builder().build()
    coffee.maker().brew()
}
