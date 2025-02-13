import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.util.Properties
import java.io.FileInputStream

plugins {
    id("com.android.application")
    kotlin("android")
    id("com.google.gms.google-services")
    id("dagger.hilt.android.plugin")
    id("kotlin-parcelize")
    id("androidx.navigation.safeargs")
    id("com.google.devtools.ksp")
    id("com.google.firebase.crashlytics")
}

android {
    namespace = "com.andrew.solokhov.mvvmmovieapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.andrew.solokhov.mvvmmovieapp"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField("String", "api_key", "\"${getProperty("local.properties", "api_key")}\"")
        buildConfigField(
            "String",
            "api_read_access_token",
            "\"${getProperty("local.properties", "api_read_access_token")}\""
        )
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
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
        jvmTarget = "17"
    }

    buildFeatures {
        buildConfig = true
        viewBinding = true
    }
    tasks.withType<KotlinCompile> {
        kotlinOptions.freeCompilerArgs = listOf("-Xcontext-receivers")
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.15.0")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.2.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")

    //ChipNavigationBar
    implementation ("com.github.ismaeldivita:chip-navigation-bar:1.4.0")

    //ViewPager2
    implementation("androidx.viewpager2:viewpager2:1.1.0")

    //Splash api
    implementation ("androidx.core:core-splashscreen:1.0.1")

    //Swipe to refresh
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.2.0-beta01")

    //Leak Canary
    debugImplementation("com.squareup.leakcanary:leakcanary-android:2.13")

    //Glide
    implementation("com.github.bumptech.glide:glide:4.16.0")

    //Paging
    val pagingVersion = "3.3.6"
    implementation("androidx.paging:paging-runtime-ktx:$pagingVersion")

    //Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:0.9.2")

    //OkHttp
    implementation(platform("com.squareup.okhttp3:okhttp-bom:4.11.0"))
    implementation("com.squareup.okhttp3:okhttp")
    implementation("com.squareup.okhttp3:logging-interceptor")

    // Dagger-Hilt
    val hiltVersion = "2.51"
    implementation("com.google.dagger:hilt-android:$hiltVersion")
    ksp("com.google.dagger:hilt-compiler:$hiltVersion")

    // Coroutines
    val coroutinesVersion = "1.8.0"
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")

    //Room
    val roomVersion = "2.6.1"
    implementation("androidx.room:room-runtime:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")
    implementation("androidx.room:room-paging:$roomVersion")
    ksp("androidx.room:room-compiler:$roomVersion")

    // Navigation
    val navigationVersion = "2.8.7"
    implementation("androidx.navigation:navigation-fragment-ktx:$navigationVersion")
    implementation("androidx.navigation:navigation-ui-ktx:$navigationVersion")

    //DataStore
    implementation("androidx.datastore:datastore-preferences:1.1.2")

    //Lottie
    val lottie = "6.3.0"
    implementation("com.airbnb.android:lottie:$lottie")

    // ViewModel
    val lifecycleVersion = "2.8.7"
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion")

    // Firebase
    implementation(platform("com.google.firebase:firebase-bom:33.9.0"))
    implementation("com.google.firebase:firebase-crashlytics-ktx")
    implementation("com.google.firebase:firebase-analytics-ktx")
    implementation("com.google.firebase:firebase-auth-ktx:")
}

fun getProperty(filename: String, propName: String): String? {
    val propsFile = rootProject.file(filename)
    if (propsFile.exists()) {
        val props = Properties()
        props.load(FileInputStream(propsFile))
        if (props[propName] != null) {
            return props[propName] as String
        } else {
            println("No such property $propName in file $filename")
        }
    } else {
        println("$filename does not exist!")
    }
    return null
}