package com.example.fppemmob_smartalertapp

import android.app.Application

class ContactsApplication : Application() {

    val database by lazy { AppDatabase.getDatabase(this) }

    val repository by lazy { ContactRepository(database.contactDao()) }

    // UPDATE: Tambahkan repository untuk history
    val alertHistoryRepository by lazy { AlertHistoryRepository(database.alertHistoryDao()) }
}