plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    kotlin("kapt")
}
kapt{
    generateStubs = true
}

android {
    signingConfigs {
        create("mundokey") {
            storeFile = file("mundo-key.jks")
            storePassword = "11111111"
            keyPassword = "11111111"
            keyAlias = "key0"
        }
    }
    namespace = "com.alex.mmop"
    compileSdk = 34
    ndkVersion = "24.0.8215888"
    defaultConfig {
        applicationId = "com.fbigl.downdevil"
        minSdk = 24
        targetSdk = 34
        versionCode = 19
        versionName = "stable-0.1.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        externalNativeBuild {
            ndkBuild {
                cppFlags += ""
            }
        }
    }
    splits.abi.apply {
        isEnable = true
        reset()
        include("armeabi-v7a", "arm64-v8a")
        isUniversalApk = true
    }


   buildTypes {
        release {
            defaultConfig.applicationId = "com.pubg"

            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig?.enableV3Signing
            signingConfig?.enableV1Signing
            signingConfig?.enableV2Signing
            signingConfig?.enableV4Signing
            signingConfig = signingConfigs.getByName("mundokey")
            multiDexEnabled = false
        }
        debug {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig?.enableV3Signing
            signingConfig?.enableV1Signing
            signingConfig?.enableV2Signing
            signingConfig?.enableV4Signing
            signingConfig = signingConfigs.getByName("mundokey")
            multiDexEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
        buildConfig = true
        viewBinding = true
    }
    sourceSets {
        getByName("main") {
            java {
                srcDirs("src/main/kotlin")
            }
        }
    }
    externalNativeBuild {
        ndkBuild {
            path = file("jni/Android.mk")
        }
    }
}

dependencies {

    implementation(fileTree("libs").include("*.aar"))
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)



    implementation("com.squareup.moshi:moshi:1.15.1")
    implementation("com.squareup.moshi:moshi-kotlin:1.15.1")
    kapt("com.squareup.moshi:moshi-kotlin-codegen:1.15.1")
    implementation("net.lingala.zip4j:zip4j:2.11.5")

    implementation ("androidx.navigation:navigation-compose:2.8.1")


// Lottie

    implementation ("com.github.ibrahimsn98:android-particles:2.0")
    implementation ("com.airbnb.android:lottie-compose:6.3.0")
    implementation ("com.google.accompanist:accompanist-systemuicontroller:0.34.0")


    // Room
//noinspection KaptUsageInsteadOfKsp
    kapt("androidx.room:room-compiler:2.6.1")



    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
