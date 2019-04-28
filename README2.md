# Dependency injection with Dagger 2 - Part 2: injecting into and from objects not managed by Dagger

Dagger is pretty straightforward when injecting objects in its dependency graph. However, there are situations where Dagger does own the lifecycle of objects. In this post, we will explore some possibilities of interacting with objects whose lifecycle is not managed by dagger.

## Injecting an external object to Dagger

Suppose we want to add a `name` property to the `Thermosiphon` class and set its value at runtime. We may think of two possibilities:

- Get a `thermosiphon` instance using Dagger and then set the value of the `name` property
- Set the name property as a constructor parameter

The first solution is simpler to implement

```kotlin
fun main(args: Array<String>) {
    val coffee = DaggerCoffeeShop.builder().build()
    
    coffee.maker().brew()
}
```

 but the send one is much cleaner because we set the name property in the constructor.