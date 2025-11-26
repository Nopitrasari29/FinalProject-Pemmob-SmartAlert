package com.example.fppemmob_smartalertapp

import android.app.Application

/**
 * Custom Application class untuk SmartAlert.
 *
 * Kelas ini berfungsi sebagai "Service Locator" sederhana.
 * Ia bertanggung jawab untuk membuat satu instance (singleton) dari Database
 * dan Repositories yang akan digunakan di seluruh aplikasi.
 */
class ContactsApplication : Application() {

    // 1. Inisialisasi Database Utama
    // Menggunakan 'by lazy' agar database hanya dibuat saat pertama kali diakses.
    val database by lazy { AppDatabase.getDatabase(this) }

    // 2. Inisialisasi Repository untuk Fitur Kontak
    // Repository ini membutuhkan ContactDao yang diambil dari database.
    val repository by lazy { ContactRepository(database.contactDao()) }

    // 3. --- TAMBAHAN BARU: Inisialisasi Repository untuk Fitur Riwayat ---
    // Repository ini membutuhkan AlertHistoryDao yang diambil dari database.
    // Dengan ini, ViewModel untuk history nanti bisa mengambil data dengan mudah.
    val alertHistoryRepository by lazy { AlertHistoryRepository(database.alertHistoryDao()) }
}