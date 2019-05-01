package com.example.dagger.kotlin

import dagger.BindsInstance
import dagger.Component
import javax.inject.Inject

// Instance not managed by Dagger
class CoffeeMakerUser {
    @Inject lateinit var coffeeMaker: CoffeeMaker
}

@Component interface CoffeeShop {
    val coffeeMaker: CoffeeMaker

    // Allows to return a new component instance
    @Component.Factory interface Factory{
        // We can define a 'create' method with custom parameters
        // The @BindsInstance allows Dagger to inject this instance in its dependecy tree
        // The method should return an instance of the component
        fun create(@BindsInstance name: String): CoffeeShop
    }

    // allows to inject object from Dagger graph to another object not in the dependy graph
    fun injectInto(coffeeMakerUser: CoffeeMakerUser)
}

fun main(args: Array<String>) {
    val coffeeShop = DaggerCoffeeShop.factory().create("My awesome thermosiphon")
    coffeeShop.coffeeMaker.brew()

    val coffeeMakerUser = CoffeeMakerUser()
    coffeeShop.injectInto(coffeeMakerUser)

    coffeeMakerUser.coffeeMaker.brew()

}
