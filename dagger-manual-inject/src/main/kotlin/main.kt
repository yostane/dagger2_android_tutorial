package com.example.dagger.kotlin

import dagger.*
import javax.inject.Inject
import javax.inject.Singleton

// Instance not managed by Dagger
class CoffeeMakerUser {
    @Inject lateinit var coffeeMaker: CoffeeMaker
}

@Component interface CoffeeShop {
    val coffeeMaker: CoffeeMaker
    // allows to inject obejct from Dagger graph to another object not in the dependy graph
    fun inject(coffeeMakerUser: CoffeeMakerUser)
}

fun main(args: Array<String>) {


    val coffeeShop = DaggerCoffeeShop.create()
    coffeeShop.coffeeMaker.pump.name = "My awesome siphon"
    coffeeShop.coffeeMaker.brew()

    val coffeeMakerUser = CoffeeMakerUser()
    DaggerCoffeeShop.create().inject(coffeeMakerUser)
    coffeeMakerUser.coffeeMaker.brew()
}
