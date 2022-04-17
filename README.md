# Photos

[![Kotlin Version](https://img.shields.io/badge/Kotlin-1.6.20-blue.svg)](https://kotlinlang.org)
[![Gradle](https://img.shields.io/badge/Gradle-7-blue?style=flat)](https://gradle.org)

Photos is a sample project by showing photos on the [Android](https://en.wikipedia.org/wiki/Android_(operating_system)) system.

## Project characteristics and tech-stack

<img src="misc/image/app_gif.gif" width="320" align="right" hspace="20">

This project takes advantage of best practices, many popular libraries and tools in the Android ecosystem. Most of the libraries are in the stable version unless there is a good reason to use non-stable dependency.

* Tech-stack
    * [100% Kotlin](https://kotlinlang.org/) + [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) - perform background operations
    * [Retrofit](https://square.github.io/retrofit/) - networking
    * [Jetpack](https://developer.android.com/jetpack)
        * [Navigation](https://developer.android.com/topic/libraries/architecture/navigation/) - in-app navigation
        * [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) - notify views about database change
        * [Lifecycle](https://developer.android.com/topic/libraries/architecture/lifecycle) - perform an action when lifecycle state changes
        * [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - store and manage UI-related data in a lifecycle conscious way
        * [Room](https://developer.android.com/jetpack/androidx/releases/room) - store offline cache
    * [Dagger](https://dagger.dev/) - dependency injection
    * [Coil](https://github.com/coil-kt/coil) - image loading library   
* Modern Architecture
    * Clean Architecture (at feature module level)
    * Single activity architecture using [Navigation component](https://developer.android.com/guide/navigation/navigation-getting-started)
    * MVVM + MVI (presentation layer)
    * [Dynamic feature modules](https://developer.android.com/studio/projects/dynamic-delivery)
    * [Android Architecture components](https://developer.android.com/topic/libraries/architecture) ([ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel), [LiveData](https://developer.android.com/topic/libraries/architecture/livedata), [Navigation](https://developer.android.com/jetpack/androidx/releases/navigation))
    * [Android KTX](https://developer.android.com/kotlin/ktx) - Jetpack Kotlin extensions
* CI
  * [GitHub Actions](https://github.com/features/actions)
  * Automatic PR verification including tests, linters and 3rd online tools
* Testing
    * [Unit Tests](https://en.wikipedia.org/wiki/Unit_testing) ([JUnit 5](https://junit.org/junit5/) via
    [android-junit5](https://github.com/mannodermaus/android-junit5))
    * [UT Tests](https://en.wikipedia.org/wiki/Graphical_user_interface_testing) ([Espresso](https://developer.android.com/training/testing/espresso))
    * [Mockk](https://mockk.io/) - mocking framework
* UI
    * [Material design](https://material.io/design)
    * Reactive UI
* Static analysis tools
    * [Ktlint](https://github.com/pinterest/ktlint) - validate code formating
    * [Detekt](https://github.com/arturbosch/detekt#with-gradle) - verify complexity look for and code smell
* Gradle
    * Plugins ([SafeArgs](https://developer.android.com/guide/navigation/navigation-pass-data#Safe-args),
    [android-junit5](https://github.com/mannodermaus/android-junit5))

## Architecture

Feature related code is placed inside one of the feature modules.
We can think about each feature as the reusable component, equivalent of [microservice](https://en.wikipedia.org/wiki/Microservices) or private library.

### Module types and module dependencies

This diagram presents dependencies between project modules (Gradle sub-projects).

![module_dependencies](https://github.com/Nor35/Photos/blob/master/misc/image/module_dependencies.JPG?raw=true)

Note that due usage of Android `dynamic-feature` module dependencies are reversed (feature modules are depending on `app` module, not another way around).

We have two kinds of modules in the application:

- `app` module - this is the main module. It contains code that wires multiple modules together (dependency injection setup, `NavHostActivity`, etc.) and fundamental application configuration (retrofit configuration, required permissions setup, custom application class, etc.).
- feature modules - the most common type of module containing all code related to a given feature.

### Feature module structure

Each feature module contains non-layer components and 3 layers with distinct set of responsibilities.

![feature_structure](https://github.com/Nor35/Photos/blob/master/misc/image/feature_structure.JPG?raw=true)

#### Presentation layer

This layer is closest to what the user sees on the screen. The `presentation` layer is a mix of `MVVM` (Jetpack `ViewModel` used to preserve data across activity restart) and
`MVI` (`actions` modify the `common state` of the view and then new state is edited to a view via `LiveData` to be rendered).

> `common state` (for each view) approach derives from
> [Unidirectional Data Flow](https://en.wikipedia.org/wiki/Unidirectional_Data_Flow_(computer_science)) and [Redux
> principles](https://redux.js.org/introduction/three-principles).

Components:
- **View (Fragment)** - presents data on the screen and pass user interactions to View Model. Views are hard to test, so they should be as simple as possible.
- **ViewModel** - dispatches (through `LiveData`) state changes to the view and deals with user interactions (these view models are not simply [POJO classes](https://en.wikipedia.org/wiki/Plain_old_Java_object)).
- **ViewState** - common state for a single view
- **NavManager** - singleton that facilitates handling all navigation events inside `NavHostActivity` (instead of separately, inside each view)

#### Domain layer

This is the core layer of the application. Notice that the `domain` layer is independent of any other layers. This allows to make domain models and business logic independent from other layers.
In other words, changes in other layers will have no effect on `domain` layer eg. changing database (`data` layer) or screen UI (`presentation` layer) ideally will not result in any code change withing `domain` layer.

Components:
- **UseCase** - contains business logic
- **DomainModel** - defies the core structure of the data that will be used within the application. This is the source of truth for application data.
- **Repository interface** - required to keep the `domain` layer independent from the `data layer` ([Dependency inversion](https://en.wikipedia.org/wiki/Dependency_inversion_principle)).

#### Data layer

Manages application data and exposes these data sources as repositories to the `domain` layer. Typical responsibilities of this layer would be to retrieve data from the internet and optionally cache this data locally.

Components:
- **Repository** is exposing data to the `domain` layer. Depending on application structure and quality of the external APIs repository can also merge, filter, and transform the data. The intention of
these operations is to create high-quality data source for the `domain` layer, not to perform any business logic (`domain` layer `use case` responsibility).

- **RetrofitService** - defines a set of API endpoints.
- **DataModel** - defines the structure of the data retrieved from the network and contains annotations, so Retrofit (Moshi) understands how to parse this network data (XML, JSON, Binary...) this data into objects.

### Data flow

Below diagram presents application data flow when a user interacts with `album screen`:

![app_data_flow](https://github.com/Nor35/Photos/blob/master/misc/image/app_data_flow.png?raw=true)

## CI pipeline

CI is utilizing [GitHub Actions](https://github.com/features/actions). Complete GitHub Actions config is located in the [.github/workflows](.github/workflows) folder.

### PR Verification

Series of workflows runs (in parallel) for every opened PR and after merging PR to `main` branch:
* `./gradlew lintDebug` - runs Android lint
* `./gradlew detekt` - runs detekt
* `./gradlew ktlintCheck` - runs ktlint
* `./gradlew testDebugUnitTest` - run unit tests
* `./gradlew connectedCheck` - run UI tests
* `./gradlew :app:bundleDebug` - create app bundle

## Upcoming improvements

- Add a list of favorite photos
- UI tests
- Create a core module

## Elapsed time

All - 54 hours

(4 hours)  Research <br />
(4 hours)  Setting up project <br />
(4 hours)  Create Navigation, Architecture <br />

(12 hours) Main screen. Album <br />
(1 hour)   Main screen. Button Add <br />
(1 hour)   Main screen. Button Reload All <br />
(2 hours)  Detail screen

(2 hours)  Skeleton for Photos <br />
(2 hours)  Skeleton for Photos <br />
(2 hours)  Light/dark theme support <br />
(8 hours)  Write tests <br />
(8 hours)  Setting up code analyzers and CI/CD <br />
(4 hours)  Making a README.md file <br />

## Getting started

There are a few ways to open this project.

### Android Studio

1. `Android Studio` -> `File` -> `New` -> `From Version control` -> `Git`
2. Enter `https://github.com/igorwojda/android-showcase.git` into URL field an press `Clone` button

### Command-line + Android Studio

1. Run `git clone https://github.com/igorwojda/android-showcase.git` command to clone project
2. Open `Android Studio` and select `File | Open...` from the menu. Select cloned directory and press `Open` button

## APK

In folder 'apk' you can find apk's application

## Inspiration

This is project is a sample, to inspire you and should handle most of the common cases, but please take a look at
additional resources.

### Cheat sheet

- [Kotlin Coroutines - Use Cases on Android](https://github.com/LukasLechnerDev/Kotlin-Coroutine-Use-Cases-on-Android) - most popular coroutine usages

### Android projects

Other high-quality projects will help you to find solutions that work for your project:

- [Android-showcase](https://github.com/igorwojda/android-showcase) - Project that presents a modern approach to Android application development
- [Theatre](https://github.com/andremion/Theatre) - Project using Clean Architecture + MVVM + Reactive Extensions + Android Architecture Components
- [Android sunflower](https://github.com/googlesamples/android-sunflower) complete `Jetpack` sample covering all
  libraries
- [Plaid](https://github.com/android/plaid) - a showcase of Android material design
- [Clean Architecture boilerplate](https://github.com/bufferapp/android-clean-architecture-boilerplate) - contains nice
  diagrams of Clean Architecture layers
- [Android samples](https://github.com/android) - official Android samples repository

## Author

[![Nor35(https://github.com/Nor35)]