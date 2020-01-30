# Dependency injection with Dagger 2 - Part 3: Basic injection in an Android Application

After exploring the basics of Dagger and some of its annotations in a console project, we are going to integrate Dagger in Android applications.

We will in the following paragraphs that this is not as simple as in console apps but also not very complex.

## Why Dagger is difficult in Android

The most notable difference between Android and console apps is that in Android, many objects are instantiated by the system.
Indeed, objects such as the Application, the activities and the fragments are instantiated by the system.
This means that Dagger cannot insatiate them, thus eliminating the constructor injection possibility. This also means that they cannot be instantiated by the developer in a module.
The consequence of this is that Dagger cannot inject into such objects (activities, fragments, etc.) because we cannot add `@Inject` to the constructor of an activity and we cannot return an activity from a `@Module`.
Thus we need to think in a different to solve this problem. We'll explore a simple solution in the next section.

_In the following paragraphs, we'll talk only about activities but you can do the same with any object created by Android._

## Injecting into activities

In order to inject into activities, there is a simple solution which consists of dynamically adding the activity to graph when the former is created. A good lifecycle function for doing this is the `onCreate` method.

When can instiate objects by itself or using modules, Dependency injection

## The starter project


## Conclusion


## Links

[Dagger 2 on Android: the shiny new @Component.Factory](https://proandroiddev.com/dagger-and-the-shiny-new-component-factory-c2234fcae6b1)