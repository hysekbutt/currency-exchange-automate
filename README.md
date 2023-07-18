# Exchange Rate
The Exchange Rate App is an Android application built using Jetpack Compose and Kotlin. It allows users to fetch and view currency and exchange rate data from a server. Users can also enter a value in a search field to see the converted rates in real-time.

# Features
- Fetches currency list and exchange rate list from a server
- Displays currency and exchange rate data in a list
- Provides a search field for users to enter values and see real-time converted rates

# Architecture
- The application follows the principles of Clean Architecture, which promotes separation of concerns and modularity. It consists of the following layers:

- Presentation Layer: Implements the user interface using Jetpack Compose. It includes screens, components, and view models.
- Domain Layer: Defines the business logic and use cases of the application. It contains entities, use case interfaces, and business logic implementations.
- Data Layer: Handles data retrieval and storage. It includes repositories, data sources, and API clients.

# Technologies Used
- Jetpack Compose: Modern UI toolkit for building the user interface
- Kotlin: Programming language used for app development
- Retrofit: HTTP client for making API requests
- Moshi: JSON parsing library for data serialization/deserialization
- Coroutines: Provides asynchronous programming and concurrency support
- Room: Local database for caching data
- Dagger Hilt: Dependency injection framework for managing dependencies

# Setup Instructions
To run the Currency Converter App locally, follow these steps:

Open the project in Android Studio.
Build and run the app on an emulator or physical device.

# License
The Currency Converter App is open-source and released under the MIT License.
