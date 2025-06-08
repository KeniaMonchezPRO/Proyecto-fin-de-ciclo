plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.tebeoteca"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.tebeoteca"
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
}

dependencies {
    implementation(libs.androidx.viewpager2)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.material3)
    implementation(libs.retrofit)
    implementation(libs.gson)
    implementation(libs.okhttplogging)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}