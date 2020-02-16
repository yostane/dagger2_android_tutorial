# Dependency injection with Dagger 2 - Part 3: Basic injection in an Android Application

Dagger is the dependency injection framework recommended by the [official documentation for most projects](https://developer.android.com/training/dependency-injection#choosing-right-di-tool).
In previous posts we have studied the basics Dagger as well as some annotations in a console project.

- [Dependency injection with Dagger 2: @Inject and @Provides](https://medium.com/@yostane/dependency-injection-with-dagger-2-inject-and-provides-ce21f7449ec5)
- [Dagger 2: injecting into and from objects not managed by Dagger](https://blog.worldline.tech/2020/01/06/dagger2_inject_into_from.html)

In this post, we are going to use this knowledge to setup Dagger in an Android project.

## Initial setup

Let's start by setting up a Dagger component in a simple Android project.
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

Next, synchronize the project in order to download the necessary dependencies.
Once the sync step completes, we can use the Dagger api and annotations in our code.
Let's define a dependency graph that has a `CoffeeMaker` which depends on `ElectricHeater` and `Thermosiphon`.
The `Thermosiphon` also depends on `ElectricHeater`.

```text
            +---->ElectricHeater
CoffeeMaker |          ^
            |          |
            |          +
            +---> Thermosiphon
```

This can be expressed in Kotlin as follows.

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

The last step of defining the dependency is to create the Dagger component.
As a reminder, the component is responsible for providing any object from the graph while satisfying its dependencies.
For example, if we get a `CoffeeMaker` from the component, it will provide instances to the `heater` and `pump` properties.

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
}
```

To validate our setup, we can log a call to the `brew()` function of the `CoffeeMaker` on the creation if the application.

```kotlin
class DiTutorialApplication : Application() {
    val appComponent = DaggerApplicationComponent.create()
    override fun onCreate() {
        super.onCreate()
        Log.d("DAGGER TUTO", "Brew: ${appComponent.getMaker().brew()}")
    }
}
```

When we launch the application, the following log should appear in logcat.

```text
2020-02-01 10:39:02.858 8090-8090/? D/DAGGER TUTO: Brew: Success
```

Our initial Dagger setup is correctly working.

The next step consists of injecting objects from the Dagger graph into activities.
That's when we start to realize that Dagger is quite complex -for now at least- to setup in Android.
Hopefully, we'll overcome the obstacles little by little.

## Why Injecting into activities is difficult in Android

The most notable difference between an Android and a console apps is that in Android, many objects are instantiated by the system.
Indeed, objects such as the Application, the activities and the fragments cannot be created by user code.
This means that Dagger cannot insatiate them, thus eliminating the constructor injection possibility.
This also means that they cannot be provided by the developer in a module.

The consequence of the previous facts is that Dagger cannot inject into such objects (activities, fragments, etc.) using the classical and simple methods.
In fact, we can neither `@Inject` to the constructor of an activity nor we can return an activity from a `@Module`.
A simple solution would be to use the `injectInto` method that we described in a previous tutorial.
We'll implement this technique in the next section.

_In the following paragraphs, we'll talk only about activities but you can do the same with any object created by Android._

## Injecting into activities

We will inject the `CoffeeMaker` into the activity after it is created.
In order to do this, we first need to add a method into the component that makes Dagger able to inject into the `MainActiivty` class even.

```kotlin
@Singleton @Component interface ApplicationComponent {
    fun injectIntoMainActivity(activity: MainActivity) // ALlows Dagger to support injecting into the MainActicity
    fun getMaker(): CoffeeMaker
}
```

Next, we can declare a `CoffeeMaker` property in the activity annotated with `@Inject`.
It is a `lateinit` property since the injection will happen after the `init` of the activity.

```kotlin
class MainActivity : AppCompatActivity() {
    @Inject lateinit var coffeeMaker: CoffeeMaker
}
```

Finally, we nicely ask the Dagger component to inject into the activity by calling `(applicationContext as DiTutorialApplication).appComponent.injectIntoMainActivity(this)`.
A good life cycle function for doing this is the `onCreate` method.

```kotlin
override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    (applicationContext as DiTutorialApplication).appComponent.injectIntoMainActivity(this)
    Log.d("Test", coffeeMaker.brew())
    setContentView(R.layout.activity_main)
}
```

As soon as the `injectIntoMainActivity` method is called, the `coffeeMaker` becomes populated.
Here is the full code of the activity.

```kotlin
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
```

Let's run the app. The label should display the result of the `brew()` function.
The downside of the technique is that we need to

![success brew](./assets/sucess-brew.png)

Great! We have successfully achieved the first integration of Dagger into an Android project.

## Conclusion

This post has illustrated how to inject objects into Android activities using Dagger.
We have seen that Dagger integration is quite complex to integrate into Android because the developer does not have the control over the creation of activities and fragment.
We still have not touched on common Android use cases such as ViewModel and famous third-party libraries. But we'll leave that to another post.

## Links

[Dagger 2 on Android: the shiny new @Component.Factory](https://proandroiddev.com/dagger-and-the-shiny-new-component-factory-c2234fcae6b1)
