plugins {
    alias(libs.plugins.zeldex.android.library)
    id("kotlin-kapt")
    id("com.google.devtools.ksp").version("1.6.10-1.0.4")
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.nickcook.zeldex.core.network"

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    // Hilt - DI
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