plugins {
  alias(libs.plugins.android.application)
  alias(libs.plugins.kotlin.android)
  alias(libs.plugins.kotlin.compose)
  alias(libs.plugins.kotlin.serialization)
  alias(libs.plugins.ksp)
  alias(libs.plugins.hilt.android)
}

android {
  namespace = "dev.ybrmst.dicodingstory"
  compileSdk = 34

  defaultConfig {
    applicationId = "dev.ybrmst.dicodingstory"
    minSdk = 26
    //noinspection OldTargetApi
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
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
  }
  kotlinOptions {
    jvmTarget = JavaVersion.VERSION_17.toString()
  }
  buildFeatures {
    compose = true
  }
}

dependencies {

  implementation(libs.androidx.core.ktx)
  implementation(libs.androidx.lifecycle.runtime.ktx)
  implementation(libs.androidx.activity.compose)
  implementation(libs.androidx.navigation.compose)
  implementation(platform(libs.androidx.compose.bom))
  implementation(libs.androidx.ui)
  implementation(libs.androidx.ui.graphics)
  implementation(libs.androidx.ui.tooling.preview)
  implementation(libs.androidx.material3)
  implementation(libs.androidx.ui.text.google.fonts)
  testImplementation(libs.junit)
  androidTestImplementation(libs.androidx.junit)
  androidTestImplementation(libs.androidx.espresso.core)
  androidTestImplementation(platform(libs.androidx.compose.bom))
  androidTestImplementation(libs.androidx.ui.test.junit4)
  debugImplementation(libs.androidx.ui.tooling)
  debugImplementation(libs.androidx.ui.test.manifest)

  //  hilt
  ksp(libs.hilt.compiler)
  implementation(libs.hilt.android)
  implementation(libs.hilt.navigation.compose)
  //  kotlin
  implementation(libs.kotlin.serialization)
  //  okhttp
  implementation(platform(libs.okhttp.bom))
  implementation(libs.okhttp)
  implementation(libs.okhttp.loggingInterceptor)
  //  retrofit
  implementation(libs.retrofit)
  implementation(libs.retrofit.serialization)
  //  skydoves sandwich
  implementation(libs.sandwich)
  implementation(libs.sandwich.retrofit)
  implementation(libs.sandwich.retrofit.serialization)
  //  coil3
  implementation(libs.coil.compose)
  implementation(libs.coil.okhttp)
  //  skydoves landscapist
  implementation(libs.landscapist.coil)
  //  orbit mvi
  implementation(libs.orbitMvi.core)
  implementation(libs.orbitMvi.viewmodel)
  implementation(libs.orbitMvi.compose)
  testImplementation(libs.orbitMvi.test)
}