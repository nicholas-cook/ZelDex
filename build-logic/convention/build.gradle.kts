
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

group = "com.nickcook.zeldex.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

dependencies {
    compileOnly("com.android.tools.build:gradle:8.3.1")
    compileOnly("com.android.tools:common:31.3.1")
    compileOnly("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.10")
}

tasks {
    validatePlugins {
        enableStricterValidation = true
        failOnWarning = true
    }
}

gradlePlugin {
    plugins {
        register("androidLibraryConvention") {
            id = "zeldex.android.library"
            implementationClass = "com.nickcook.zeldex.buildlogic.convention.AndroidLibraryConventionPlugin"
        }
    }
}
