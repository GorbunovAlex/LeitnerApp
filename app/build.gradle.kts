import org.gradle.internal.impldep.com.fasterxml.jackson.core.JsonPointer.compile

plugins {
    alias(libs.plugins.android.application)
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "alexgorbunov.space.leitnerapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "alexgorbunov.space.leitnerapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    implementation(libs.datastore.rxjava3)
    implementation(libs.datastore.preferences.rxjava3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    implementation(libs.hilt.android)
    annotationProcessor(libs.hilt.android.compiler.v244)

    implementation(libs.jackson.core)
    implementation(libs.jackson.annotations)
    implementation(libs.jackson.databind)

    implementation(libs.datastore.preferences)
}