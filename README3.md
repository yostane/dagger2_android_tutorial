# Dependency injection with Dagger 2 - Part 3: Basic injection in an Android Application

After exploring the basics of Dagger and some of its annotations in a console project, we are going to integrate Dagger in Android applications.

We will in the following paragraphs that this is not as simple as in console apps but also not very complex.

## Initial setup

Let's start our journey by setting up a Dagger component in an simple Android project. 
Launch Android Studio and create a single activity project.
Once the project is created, open the _build.gradle_ of the _app_ (located in _app/build.gradle_) and the following lines:

Add the kotlin-kapt (annotation processor) at the beginning of the file, if not already added.

```groovy
apply plugin: 'kotlin-kapt'
```

In the dependencies part, add the Dagger library and annotation processor.

```groovy
def DAGGER_VERSION = "2.25.4"
implementation "com.google.dagger:dagger:${DAGGER_VERSION}"
kapt "com.google.dagger:dagger-compiler:${DAGGER_VERSION}"
```

Next, synchronize the project in order to download the necessary dependencies. After that, we can define a dependency graph that has a `CoffeeMaker` which depends on `ElectricHeater` and `Thermosiphon`. The  `Thermosiphon` also depends on `ElectricHeater`.

```text
            +---->ElectricHeater
CoffeeMaker |          ^
            |          |
            |          +
            +---> Thermosiphon
```

This can be express in Kotlin as follows.

```kotlin
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
```

The last step is to create the Dagger component. As a reminder, the component is the object that provides objects from the graph with the dependencies satisfied. For example, if we get a `CoffeeMaker` from the component, it will provide instances to the `heater` and `pump` properties.

```kotlin
@Singleton @Component interface ApplicationComponent {
    fun getMaker(): CoffeeMaker
}
```

_For more information about the `@Inject, @Singleton and @Component` annotations, please read my previous articles._

Since a component has generally the same life cycle as the application, we usually define the former as a property of the Application class.

```kotlin
class DiTutorialApplication : Application() {
    // DaggerApplicationComponent is the implementation of ApplicationComponent provided by Dagger
    val appComponent = DaggerApplicationComponent.create()
    override fun onCreate() {
        super.onCreate()
        Log.d("DAGGER TUTO", "Brew: ${appComponent.getMaker().brew()}")
    }
}
```

 

```text
2020-02-01 10:39:02.858 8090-8090/? D/DAGGER TUTO: Brew: Success
```

## Why Dagger is difficult in Android

The most notable difference between Android and console apps is that in Android, many objects are instantiated by the system.
Indeed, objects such as the Application, the activities and the fragments are instantiated by the system.
This means that Dagger cannot insatiate them, thus eliminating the constructor injection possibility. This also means that they cannot be instantiated by the developer in a module.
The consequence of this is that Dagger cannot inject into such objects (activities, fragments, etc.) because we cannot add `@Inject` to the constructor of an activity and we cannot return an activity from a `@Module`.
Thus we need to think in a different to solve this problem. We'll explore a simple solution in the next section.

_In the following paragraphs, we'll talk only about activities but you can do the same with any object created by Android._

## Injecting into activities

In order to inject into activities, there is a simple solution which consists of dynamically adding the activity to dependency graph when the former is created. A good life cycle function for doing this is the `onCreate` method. This will allow Dagger to inject any object that has the `@Inject` annotation inside the activity.



When can instiate objects by itself or using modules, Dependency injection

## The starter project


## Conclusion


## Links

[Dagger 2 on Android: the shiny new @Component.Factory](https://proandroiddev.com/dagger-and-the-shiny-new-component-factory-c2234fcae6b1)