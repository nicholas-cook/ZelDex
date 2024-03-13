# ZelDex

A sample application that gives users access to the Hyrule Compendium from Breath of the Wild by leveraging the [Hyrule Compendium API](https://gadhagod.github.io/Hyrule-Compendium-API/#/)

## Overview
Users can select which of five categories they would like to view, or they can view all categories at once. The app will display all entries for the given category. Users can click on an individual entry and view its details.

## Architecture
The app is written in Kotlin and uses Jetpack Compose for creating views. It follows the MVVM pattern for separation of concerns.
Different functionality is split into separate modules.
- app: View and view models
- core: Houses all functionality outside of Android-dependent code
- core:data: Includes repositories for fetching data and model classes to represent that data
- core:localstorage: Includes a Room database for offline storage of data
- core:network: Includes code to fetch data from the remote Hyrule Compendium API
- core:testing: Includes common code that can be used for UI and unit tests
