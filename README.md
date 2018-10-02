# Dependency injection on Android + Kotlin with Dagger 2

Dependency Injection, or DI in short, is a design pattern that allows to delegate the creation of objects and theire dependencies to another object or framework. It is gaining a lot interest in Android development. In this post, we will focus on dependency inection using the Dagger 2 Framework.

## Introduction

Dagger 2 is a dependency injection developped by Google. It is not to be confused with the Dagger (or Dagger 1) Framework which is older and deprecated.

Dagger allow to define and configure dependcies using annotations. It also allows to inject into Android componenets such as Activities and Fragments thanks to Android-Dagger. In fact, Dagger 2 can be devided into different parts:

- Dagger: which provides the base dependency inejction capabilities
- Dagger-Android: which allows to inject objects into Android components such as Activities and Fragments

We will start by studying to inject dependencies on a console application. After that, we will create an Android project and use Dagger-Android to take full advantage of dependcy injection in every component of the app.

The sample project of this guide are created using IntteliJ and Android Studio. The programming language is Kotlin.

Let's get straight to the first topic: dagger in a console app.

## Dagger DI through a simple app

In this part, we will develop a console app that illustrates how to apply DI with dagger.

First, I will describe the problem that we are going to solve. Suppose we want to model a coffee maker that requires a thermosiphon pump and an electric heater to function.

The solution of this problem is pretty straightforward. We will need three classes: `ElectricHeader`, `Thermosiphon` and `CoffeeMaker`. The latter requires a instance of an `ElectricHeader` and `Thermosiphon` so that it can turn them on or off when the `CoffeeMaker` is turned on or off. In addition to that, the `Thermosiphon` needs the same `ElectricHeader` instance of the coffee maker.

Without dependency injection, we would need to create the instances manually and the code should be similar to the one below:

```kotlin
val heater = ElectricHeater()
val pump = Thermosiphon(heater)
val coffeMaker = CoffeeMaker(heater, pump)
coffeeMaker.brew()
```

With dependency injection, the object instantiation will be delegated to another class or framework. Thus the previous code would look like this:

```kotlin
val coffeMaker = DIClassOrFramework.getCoffeeMaker()
coffeeMaker.brew()
```

It may seem like magic but unfortunately not. This kind of code is achieved by Dagger2 that to some annotations and some lines of additional code. The good news is that the code and annotations that we add will make our business code smaller, and easier to test. Next, we will see how to make a coffee maker with Dagger2.

Dagger 2 works by building a dependency graph. We call it a dependency graph because when we imagine objects and their dependencies, it is similar to a graph. In our case the graph looks as follows:

```
            +---->ElectricHeater
CoffeeMaker |          ^
            |          |
            |          |
            |          +
            +---> Thermosiphon
```

The graph has a root item which is the CoffeeMaker that has two dependencies illustrated by the arrows. Dagger 2 manages the dependency graph of the classes that have the `@Inject` and `@Provides` annotation. Dagger cannot instantiate or inject classes that do not have neither `@Inject` nor `@Provides` annotations. In order to get an instance of the root object (CoffeeMaker), we need to create an interface that has a function that returns the root class of the graph. This interface has to have to `@Componenet` annotation.

Here is a summary of our first Dagger 2 annotations:

- `@Inject` and `@Provides`: class that have this annotations are integrated in the dependency graph of Dagger.
- `@Component`: used to annotate the interface that returns the root object of the graph.

Let's apply this knowledge to our problem. In the following we specify the dependency graph only with `@Inject` annotation. The `@Provides` annotation offers more possibilities but requires more code. So, we will stick with `@Inject` here.

```kotlin
class ElectricHeater @Inject constructor() {
    var heating: Boolean = false
    fun on() {
        this.heating = true
    }
    fun off() {
        this.heating = false
    }
    val isHot get() = heating
}

class Thermosiphon @Inject constructor( private val heater: ElectricHeater ) {
    fun pump() {
        println("Thermosiphon is pumping")
        if (heater.isHot) {
            println("Heater is hot !!!")
        }
    }
}

class CoffeeMaker @Inject constructor(private val heater: ElectricHeater) {
    @Inject private val pump: Thermosiphon
    fun brew() {
        println("Brewing coffee")
        heater.on()
        pump.pump()
        println("Coffee ready :)")
        heater.off()
    }
}
```

## Dagger in an Android app

## Links

- [General explanation of Dagger 2](http://www.vogella.com/tutorials/Dagger/article.html)
- [Main tutorial on Dagger and Android](https://google.github.io/dagger/android)
- [More detailed tutorial](https://github.com/Vovaxo/sample-dagger2)
- [Sack overflow on DaggerApplication](https://stackoverflow.com/questions/49589467/how-to-work-with-daggerapplication-and-daggerappcompatactivity)
- [Sample app that extends Dagger Application](https://github.com/iammert/dagger-android-injection) and its [medium post](https://medium.com/@iammert/new-android-injector-with-dagger-2-part-1-8baa60152abe)
