plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
}

val exoPlayerVersion = "1.4.0-beta01"

android {
    namespace = "com.unit1337.rtsp2"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.unit1337.rtsp2"
        minSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // player
    implementation("com.google.android.material:material:1.9.0")

    implementation("androidx.media3:media3-ui:$exoPlayerVersion")
    implementation("androidx.media3:media3-exoplayer:$exoPlayerVersion")
    implementation("androidx.media3:media3-exoplayer-rtsp:$exoPlayerVersion")

}
