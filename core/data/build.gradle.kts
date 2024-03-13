plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("kotlin-kapt")
    alias(libs.plugins.hilt)
    id("com.google.devtools.ksp").version("1.6.10-1.0.4")
}

android {
    namespace = "com.nickcook.zeldex.core.data"
    compileSdk = 34

    defaultConfig {
        minSdk = 23

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    // Hilts - DI
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    // Other modules in the app
    implementation(project(":core:network"))
    implementation(project(":core:localstorage"))

    testImplementation(libs.junit)
}