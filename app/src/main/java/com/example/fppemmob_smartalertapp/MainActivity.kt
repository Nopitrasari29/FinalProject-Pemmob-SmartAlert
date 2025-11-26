package com.example.fppemmob_smartalertapp

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

/**
 * MainActivity: Wadah utama aplikasi yang mengelola Navigasi, Menu Bawah, dan Sensor Guncangan.
 */
class MainActivity : AppCompatActivity() {

    // Variabel untuk sensor guncangan (ShakeDetector)
    private lateinit var shakeDetector: ShakeDetector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // ============================================================
        // 1. SETUP NAVIGASI & BOTTOM BAR
        // ============================================================

        // Menemukan NavHostFragment (Area ganti-ganti halaman)
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        // Mengambil NavController (Pengontrol navigasi)
        val navController = navHostFragment.navController

        // Menemukan BottomNavigationView (Menu Bawah)
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        // Menghubungkan Bottom Bar dengan Navigasi
        // Ini membuat klik pada ikon menu otomatis pindah ke halaman yang sesuai
        bottomNavigationView.setupWithNavController(navController)

        // CATATAN:
        // Sesuai mockup, kita TIDAK menyembunyikan Bottom Bar di halaman manapun.
        // Jadi menu Home, Safety, Contacts, Settings akan selalu terlihat.

        // ============================================================
        // 2. SETUP SENSOR GUNCANGAN (SHAKE DETECTOR)
        // ============================================================

        shakeDetector = ShakeDetector(this) {
            // Kode ini berjalan otomatis saat HP diguncang keras
            Log.d("ShakeDetector", "GUNCANGAN TERDETEKSI DI MAINACTIVITY!")

            // Tampilkan pesan feedback ke user
            Toast.makeText(this, "Shake Detected! Initiating SOS...", Toast.LENGTH_SHORT).show()

            // Opsi Tambahan: Jika ingin otomatis pindah ke halaman Alert saat diguncang
            // Buka komentar di bawah ini jika ID action di nav_graph.xml sudah benar:
            /*
            try {
                navController.navigate(R.id.alertFragment)
            } catch (e: Exception) {
                Log.e("MainActivity", "Navigasi otomatis gagal: ${e.message}")
            }
            */
        }
    }

    // ============================================================
    // 3. SIKLUS HIDUP APLIKASI (LIFECYCLE)
    // ============================================================

    override fun onResume() {
        super.onResume()
        // Aktifkan sensor saat aplikasi sedang dibuka/dilihat user
        shakeDetector.start()
        Log.d("ShakeDetector", "Shake detector started.")
    }

    override fun onPause() {
        super.onPause()
        // Matikan sensor saat aplikasi diminimize atau layar mati untuk hemat baterai.
        // (Nanti tugas monitoring background akan dihandle oleh Service, bukan Activity ini)
        shakeDetector.stop()
        Log.d("ShakeDetector", "Shake detector stopped.")
    }
}