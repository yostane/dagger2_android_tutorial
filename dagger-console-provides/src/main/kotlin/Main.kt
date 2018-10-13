package com.example.dagger.kotlin

import dagger.*
import javax.inject.Inject
import javax.inject.Singleton

// base classes

interface Heater {
    fun isHot() : Boolean
    fun on()
    fun off()
}

class Thermosiphon @Inject constructor( private val heater: Heater ) {
    init {
        println("++++Thermosiphon instantiated")
    }
    fun pump() {
        println("Thermosiphon is pumping")
        if (heater.isHot() ) {
            println("Heater is hot !!!")
        }
    }
}

class CoffeeMaker @Inject constructor() {
    @Inject lateinit var heater: Heater
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

// Electric Heater coffee maker

class ElectricHeater : Heater {
    var heating: Boolean = false
    init {
        println("++++Electric heater instantiated")
    }
    override fun isHot() = heating
    override fun on() {
        println("Electric heater on")
        this.heating = true
    }
    override fun off() {
        println("Electric heater off")
        this.heating = false
    }
}

@Module class CoffeeMachineModule {
    @Provides @Singleton fun heaterProvider() : Heater = ElectricHeater()
}

@Singleton @Component(modules = [CoffeeMachineModule::class]) interface CoffeeShop {
    fun maker(): CoffeeMaker
}

// coffee maker with Fire Heater

class FireHeater : Heater {
    var heating: Boolean = false
    init {
        println("++++Fire header instantiated")
    }
    override fun isHot() = heating
    override fun on() {
        println("Fire heater on")
        this.heating = true
    }
    override fun off() {
        println("Fire heater off")
        this.heating = false
    }
}

@Module class FireCoffeeMachineModule {
    @Provides @Singleton fun heaterProvider() : Heater = FireHeater()
}

@Singleton @Component(modules = [FireCoffeeMachineModule::class]) interface FireCoffeeShop {
    fun maker(): CoffeeMaker
}

fun main(args: Array<String>) {
    val coffee = DaggerCoffeeShop.builder().build()
    coffee.maker().brew()

    val fireCoffee = DaggerFireCoffeeShop.builder().build()
    fireCoffee.maker().brew()
}
