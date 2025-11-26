package com.example.fppemmob_smartalertapp

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

/**
 * MainActivity adalah satu-satunya Activity di aplikasi ini.
 * Ia berfungsi sebagai "host" atau "wadah" untuk semua halaman (Fragment).
 * Ia juga bertanggung jawab atas komponen UI global seperti Bottom Navigation
 * dan logika global seperti deteksi guncangan (ShakeDetector).
 */
class MainActivity : AppCompatActivity() {

    // --- TAMBAHAN BARU ---
    // Mendeklarasikan variabel untuk ShakeDetector.
    // 'private lateinit var' berarti kita berjanji akan menginisialisasi variabel ini nanti.
    private lateinit var shakeDetector: ShakeDetector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // --- Kode Navigasi yang Sudah Ada (Sangat Baik!) ---
        // 1. Temukan NavHostFragment, yang merupakan "wadah" untuk semua Fragment.
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        // 2. Dapatkan NavController dari NavHostFragment. NavController adalah "otak" navigasi.
        val navController = navHostFragment.navController

        // 3. Temukan BottomNavigationView di layout.
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        // 4. Hubungkan BottomNavigationView dengan NavController.
        //    Ini secara otomatis akan menangani klik pada item menu navigasi.
        bottomNavigationView.setupWithNavController(navController)
        // --- Akhir Kode Navigasi ---


        // --- TAMBAHAN BARU: Inisialisasi ShakeDetector ---
        // Kita membuat instance dari ShakeDetector.
        // 'this' merujuk ke MainActivity sebagai Context.
        // Blok kode di dalam { } adalah lambda function yang akan dijalankan setiap kali
        // guncangan terdeteksi (ini adalah parameter onShake).
        shakeDetector = ShakeDetector(this) {
            // Kode di dalam blok ini akan dijalankan setiap kali HP diguncang keras.

            // Untuk sekarang, kita hanya menampilkan pesan Toast dan Log untuk pengujian.
            Log.d("ShakeDetector", "GUNCANGAN TERDETEKSI DI MAINACTIVITY!")
            Toast.makeText(this, "Guncangan Terdeteksi!", Toast.LENGTH_SHORT).show()

            // DI MASA DEPAN: Kode ini akan diganti dengan logika untuk memulai alur darurat.
            // Contoh: navController.navigate(R.id.action_global_alertPage)
            // Ini akan memindahkan pengguna ke halaman Alert Page (countdown 5s).
        }
    }

    /**
     * onResume() adalah bagian dari siklus hidup Activity.
     * Fungsi ini dipanggil setiap kali aplikasi kembali ke layar (menjadi aktif).
     * Ini adalah tempat terbaik untuk mulai mendengarkan sensor.
     */
    override fun onResume() {
        super.onResume()
        // Memulai listener sensor.
        shakeDetector.start()
        Log.d("ShakeDetector", "Shake detector started.")
    }

    /**
     * onPause() adalah bagian dari siklus hidup Activity.
     * Fungsi ini dipanggil saat aplikasi tidak lagi di layar (misalnya, pengguna menekan
     * tombol home atau pindah ke aplikasi lain).
     * Sangat PENTING untuk berhenti mendengarkan sensor di sini untuk menghemat baterai.
     */
    override fun onPause() {
        super.onPause()
        // Menghentikan listener sensor.
        shakeDetector.stop()
        Log.d("ShakeDetector", "Shake detector stopped.")
    }
}