pluginManagement {
    includeBuild("build-logic")
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "ZelDex"
include(":app")
include(":core")
include(":core:network")
include(":core:data")
include(":core:localstorage")
include(":core:testing")

// Solution taken from https://issuetracker.google.com/issues/315023802#comment18
gradle.startParameter.excludedTaskNames.addAll(listOf(":build-logic:convention:testClasses"))
