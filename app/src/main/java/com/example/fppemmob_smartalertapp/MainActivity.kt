package com.example.fppemmob_smartalertapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

/**
 * MainActivity adalah layar utama aplikasi yang pertama kali muncul.
 * Tanggung jawabnya adalah menampilkan UI utama dan menangani interaksi awal pengguna.
 */
class MainActivity : AppCompatActivity() {

    /**
     * Fungsi onCreate dipanggil saat Activity pertama kali dibuat.
     * Di sinilah kita menghubungkan layout XML dengan kode Kotlin dan
     * menyiapkan listener untuk tombol.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Menghubungkan file layout activity_main.xml ke kelas Kotlin ini
        setContentView(R.layout.activity_main)

        // --- Logika untuk Tombol Pengaturan ---

        // 1. Mencari tombol di file layout berdasarkan ID yang sudah kita tentukan.
        //    Kita menyimpannya dalam sebuah variabel bernama settingsButton.
        val settingsButton: Button = findViewById(R.id.settings_button)

        // 2. Menambahkan "pendengar" (listener) pada tombol tersebut.
        //    Blok kode di dalam { } akan dijalankan setiap kali tombol ini diklik.
        settingsButton.setOnClickListener {
            // 3. Membuat "Intent", yaitu sebuah permintaan untuk melakukan sesuatu.
            //    Dalam kasus ini, permintaannya adalah untuk memulai (pindah ke) SettingsActivity.
            val intent = Intent(this, SettingsActivity::class.java)

            // 4. Menjalankan Intent tersebut, yang akan memicu perpindahan layar.
            startActivity(intent)
        }

        // --- Batas Logika Tombol Pengaturan ---

        // (Nantinya, logika untuk tombol panik dan komponen lain akan ditambahkan di sini)
    }
}