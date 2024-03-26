plugins {
    alias(libs.plugins.zeldex.android.library)
}

android {
    namespace = "com.nickcook.zeldex.core.testing"
}

dependencies {

    api(project(":core:data"))
    api(project(":core:localstorage"))
    api(project(":core:network"))

    api(libs.kotlinx.coroutines.test)
    implementation(libs.junit4)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}