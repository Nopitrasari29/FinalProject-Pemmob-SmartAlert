package com.example.fppemmob_smartalertapp

import kotlinx.coroutines.flow.Flow

class AlertHistoryRepository(private val alertHistoryDao: AlertHistoryDao) {

    val allHistory: Flow<List<AlertHistoryEntity>> = alertHistoryDao.getAllHistory()

    suspend fun insert(history: AlertHistoryEntity) {
        alertHistoryDao.insertHistory(history)
    }

    suspend fun clearAll() {
        alertHistoryDao.clearAllHistory()
    }
}