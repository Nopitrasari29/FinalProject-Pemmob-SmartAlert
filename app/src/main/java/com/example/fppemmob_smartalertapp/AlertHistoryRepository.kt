package com.example.fppemmob_smartalertapp

import kotlinx.coroutines.flow.Flow

class AlertHistoryRepository(private val alertHistoryDao: AlertHistoryDao) {

    // Mengambil data riwayat
    val allHistory: Flow<List<AlertHistoryEntity>> = alertHistoryDao.getAllHistory()

    // Menyimpan data riwayat
    suspend fun insert(history: AlertHistoryEntity) {
        alertHistoryDao.insertHistory(history)
    }

    // Menghapus semua riwayat
    suspend fun clearAll() {
        alertHistoryDao.clearAllHistory()
    }
}