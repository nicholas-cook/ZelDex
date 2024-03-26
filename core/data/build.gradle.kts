plugins {
    alias(libs.plugins.zeldex.android.library)
    id("kotlin-kapt")
    alias(libs.plugins.hilt)
    id("com.google.devtools.ksp").version("1.6.10-1.0.4")
}

android {
    namespace = "com.nickcook.zeldex.core.data"
}

dependencies {
    // Hilt - DI
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    // Other modules in the app
    implementation(project(":core:network"))
    implementation(project(":core:localstorage"))

    testImplementation(project(":core:testing"))
    testImplementation(libs.junit)
    testImplementation(libs.assertk)
}