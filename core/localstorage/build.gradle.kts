plugins {
    alias(libs.plugins.zeldex.android.library)
    id("com.google.devtools.ksp").version("1.6.10-1.0.4")
    id("kotlin-kapt")
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.nickcook.zeldex.core.localstorage"
}

dependencies {
    // Room - local storage
    implementation(libs.room.ktx)
    implementation(libs.room.runtime)
    kapt(libs.room.compiler)

    // Hilt - DI
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    testImplementation(libs.junit)
}