package com.example.fppemmob_smartalertapp

import android.app.Application

/**
 * Custom Application class untuk SmartAlert.
 *
 * Kelas ini diinisialisasi sekali saat aplikasi pertama kali dijalankan.
 * Tujuannya adalah untuk menyediakan instance global (singleton) dari
 * Database dan Repository yang bisa diakses dari mana saja di dalam aplikasi.
 *
 * Dengan cara ini, kita memastikan hanya ada satu koneksi ke database yang dibuat,
 * yang sangat penting untuk efisiensi dan konsistensi data.
 */
class ContactsApplication : Application() {

    // Menggunakan 'by lazy' adalah teknik inisialisasi yang cerdas.
    // Kode di dalam { } hanya akan dijalankan saat 'database' pertama kali diakses,
    // bukan saat aplikasi dimulai. Ini mempercepat waktu startup aplikasi.
    val database by lazy { AppDatabase.getDatabase(this) }

    // Sama seperti database, repository juga dibuat secara lazy, dan menggunakan
    // instance database yang sudah dibuat sebelumnya.
    val repository by lazy { ContactRepository(database.contactDao()) }
}