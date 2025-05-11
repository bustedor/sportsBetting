# SportsBetting App

## Overview

SportsBetting is an Android application designed to provide users with information about sports events, allow them to
view event details, and manage a betting basket.

## Features

* **Sports Bulletin:** Displays a list or feed of upcoming or current sports events.
* **Event Details:** Shows detailed information about a selected sports event including odds.
* **Betting Basket:** Allows users to view/remove placed bets.

## Architecture

The project follows a modular design, with features separated into their own packages (e.g., `bulletin`, `detail`,
`basket`). Within each feature module, the structure suggests an adherence to **Clean Architecture** principles, with
distinct layers for:

* **`data`**: Handles data sources (remote/local), repositories, and data models.
* **`domain`**: Contains use cases, business logic, and domain-specific models/entities.
* **`presentation`**: Manages UI-related components (ViewModels, Composable functions).
* **`di`**: Contains Hilt modules for dependency injection.

Navigation is handled using Jetpack Navigation Compose, with a centralized `navigation` package and feature-specific
navigation graphs.

## Technologies Used

* **UI Toolkit:** Jetpack Compose
* **Dependency Injection:** Hilt
* **Networking:** Retrofit, OkHttp
* **Async Operations:** Coroutines
* **Analytics:** Firebase Analytics
* **AndroidX Libraries:** Core KTX, Lifecycle, Activity Compose, Navigation Compose, Material3
