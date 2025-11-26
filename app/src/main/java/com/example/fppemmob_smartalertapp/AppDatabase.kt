package com.example.fppemmob_smartalertapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// PENTING: Tambahkan AlertHistoryEntity ke daftar entities DAN ubah version menjadi 2
@Database(entities = [ContactEntity::class, AlertHistoryEntity::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun contactDao(): ContactDao

    // --- TAMBAHAN BARU ---
    abstract fun alertHistoryDao(): AlertHistoryDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "smartalert_database"
                )
                    // PENTING: Tambahkan ini agar aplikasi tidak crash saat versi database berubah
                    // Ini akan menghapus data lama jika skema berubah (aman untuk tahap dev)
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}