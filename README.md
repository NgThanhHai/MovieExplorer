# An App with some features to view movie information from TMDB
## Prerequisites
Create the `local.properties` file in the APP's Module folder. This file contains your API key, API base and API base URL endpoint. Example:
```
API_KEY=47aa75b56464da7a186b813xxxxxxxxx
BASE_URL=https://api.themoviedb.org/3/
BASE_URL_IMAGE=https://image.tmdb.org/t/p/w500
```
## Screenshots Demo
<img src="https://github.com/NgThanhHai/MovieExplorer/blob/main/Screenshot_1717749256.png?raw=true" width="280" /> <img src="https://github.com/NgThanhHai/MovieExplorer/blob/main/Screenshot_1717749259.png?raw=true" width="280" />

## Tech Stack 
- Jetpack Compose - Jetpack Compose is a modern toolkit for building native Android UI, enabling developers to create beautiful and responsive applications using a declarative and intuitive Kotlin-based API.
- Kotlin - Officially supported programming language for Android development.
- MVVM - MVVM (Model-View-ViewModel) is a software architectural pattern that separates the user interface (View), business logic (Model), and the UI logic (ViewModel) to enhance the modularity, testability, and maintainability of the application.
- Koin - Koin is a lightweight dependency injection framework for Kotlin, designed to be simple, pragmatic, and easy to integrate into your Kotlin and Android projects.
- Coroutines - Coroutines are a concurrency design pattern in Kotlin that simplify asynchronous programming by allowing you to write code in a sequential manner while performing non-blocking operations.
- RoomDB - For local storage & caching data.
- Android Architecture Components -Android Architecture Components are a set of libraries provided by Google that help you design robust, testable, and maintainable Android applications.
- Flow - provides a type-safe way to work with asynchronous sequences of values
- Retrofit - A type-safe HTTP client for Android and Java.

SDK levels supported
--------------------
- Minimum SDK 24
- Target SDK 34
