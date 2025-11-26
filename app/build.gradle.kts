plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    // --- TAMBAHAN WAJIB untuk Room ---
    // Plugin ini dibutuhkan untuk memproses anotasi Room (@Entity, @Dao, dll)
    id("kotlin-kapt")
}

android {
    namespace = "com.example.fppemmob_smartalertapp"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.fppemmob_smartalertapp"
        minSdk = 24
        targetSdk = 36
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
        // Direkomendasikan untuk menggunakan Java 17 untuk proyek modern
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    // Mengaktifkan viewBinding untuk akses view yang lebih aman dan mudah
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    //====================================================================
    // CORE & UI DEPENDENCIES (Fondasi Aplikasi)
    //====================================================================
    // Kumpulan library dasar untuk fungsionalitas inti Android & Kotlin
    implementation(libs.androidx.core.ktx)
    // Library untuk dukungan kompatibilitas fitur-fitur UI modern di versi Android lama
    implementation(libs.androidx.appcompat)
    // Library untuk komponen Material Design (Button, CardView, BottomNavigationView, dll)
    implementation(libs.material)
    // Library untuk mengelola siklus hidup Activity
    implementation(libs.androidx.activity)
    // Library untuk membuat layout yang kompleks dan responsif
    implementation(libs.androidx.constraintlayout)


    //====================================================================
    // NAVIGATION COMPONENT (Pengatur Perpindahan Antar Halaman)
    //====================================================================
    // Komponen utama untuk mengelola navigasi antar Fragment (Kotlin KTX)
    implementation(libs.androidx.navigation.fragment.ktx)
    // Komponen untuk mengintegrasikan Navigation Component dengan komponen UI (misal: BottomNavigationView)
    implementation(libs.androidx.navigation.ui.ktx)


    //====================================================================
    // GOOGLE MAPS (Fitur Peta)
    //====================================================================
    // Library untuk menampilkan dan berinteraksi dengan Google Maps di aplikasi
    implementation(libs.play.services.maps)


    //====================================================================
    // ROOM DATABASE (Penyimpanan Data Lokal) - TAMBAHAN
    //====================================================================
    // Library utama Room untuk fungsionalitas database
    implementation(libs.androidx.room.runtime)
    // Anotation processor untuk Room, memproses @Entity, @Dao, dll. saat kompilasi
    kapt(libs.androidx.room.compiler)
    // Ekstensi Kotlin (KTX) untuk Room, memberikan dukungan untuk Coroutines (Flow, suspend function)
    implementation(libs.androidx.room.ktx)
    // Lifecycle Components (ViewModel, LiveData)
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.3")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.8.3")
    implementation("androidx.activity:activity-ktx:1.9.0")

    // Google Play Services untuk Fused Location Provider (GPS yang efisien)
    implementation("com.google.android.gms:play-services-location:21.3.0") // <-- KESALAHAN SUDAH DIPERBAIKI DI SINI


    //====================================================================
    // TESTING DEPENDENCIES (Untuk Pengujian Kode)
    //====================================================================
    // Library dasar untuk unit testing
    testImplementation(libs.junit)
    // Library untuk instrumentation testing di Android
    androidTestImplementation(libs.androidx.junit)
    // Library untuk UI testing di Android
    androidTestImplementation(libs.androidx.espresso.core)
}