# Dependency injection on Android + Kotlin with Dagger 2

Dependency Injection is a design pattern that allows to delegate the creation of objects and theire dependencies to another object or framework. It is gaining a lot interest in Android development. In this post, we will focus on dependency inection using the Dagger 2 Framework.

## Introduction

Dagger 2 is a dependency injection developped by Google. It is not to be confused with the Dagger (or Dagger 1) Framework which is older and deprecated.

Dagger allow to define and configure dependcies using annotations. It also allows to inject into Android componenets such as Activities and Fragments thanks to Android-Dagger. In fact, Dagger 2 can be devided into different parts:

- Dagger: which provides the base dependency inejction capabilities
- Dagger-Android: which allows to inject objects into Android components such as Activities and Fragments

We will start by studying to inject dependencies on a console application. After that, we will create an Android project and use Dagger-Android to take full advantage of dependcy injection in every component of the app.

Let's get straight to the first topic: dagger in a console app.

## Dagger in a console app

In this part, we will create a console app using ItelliJ.

## Dagger in an Android app

## Links

- [General explanation of Dagger 2](http://www.vogella.com/tutorials/Dagger/article.html)
- [Main tutorial on Dagger and Android](https://google.github.io/dagger/android)
- [More detailed tutorial](https://github.com/Vovaxo/sample-dagger2)
- [Sack overflow on DaggerApplication](https://stackoverflow.com/questions/49589467/how-to-work-with-daggerapplication-and-daggerappcompatactivity)
- [Sample app that extends Dagger Application](https://github.com/iammert/dagger-android-injection) and its [medium post](https://medium.com/@iammert/new-android-injector-with-dagger-2-part-1-8baa60152abe)
