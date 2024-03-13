plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("kotlin-kapt")
    id("com.google.devtools.ksp").version("1.6.10-1.0.4")
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.nickcook.zeldex.core.network"
    compileSdk = 34

    defaultConfig {
        minSdk = 23

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }
    buildFeatures {
        buildConfig = true
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

dependencies {
    // Hilts - DI
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    // OkHttp - Networking
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging)

    // Retrofit - API calls
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.moshi)

    // Moshi - JSON parsing
    implementation(libs.moshi.kotlin)
    implementation(libs.moshi.adapter)
    implementation(libs.moshi.codegen)
    ksp(libs.moshi.codegen)

    // Sandwich - API response wrapper
    api(libs.sandwich)

    testImplementation(libs.junit)
}