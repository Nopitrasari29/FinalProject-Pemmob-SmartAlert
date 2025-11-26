package com.example.fppemmob_smartalertapp

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AlertHistoryDao {
    @Insert
    suspend fun insertHistory(history: AlertHistoryEntity)

    // Mengambil data diurutkan dari yang paling baru (DESC)
    @Query("SELECT * FROM alert_history ORDER BY timestamp DESC")
    fun getAllHistory(): Flow<List<AlertHistoryEntity>>

    @Query("DELETE FROM alert_history")
    suspend fun clearAllHistory()
}