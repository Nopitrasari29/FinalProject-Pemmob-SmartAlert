package com.example.fppemmob_smartalertapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// UPDATE: Version naik jadi 2, dan tambahkan AlertHistoryEntity
@Database(entities = [ContactEntity::class, AlertHistoryEntity::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun contactDao(): ContactDao

    // UPDATE: Tambahkan akses ke DAO baru
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
                    // PENTING: Agar tidak crash saat update versi database
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}