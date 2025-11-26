package com.example.fppemmob_smartalertapp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "alert_history")
data class AlertHistoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val timestamp: Long, // Waktu kejadian (milidetik)
    val location: String, // Alamat atau Koordinat
    val aiAnalysisResult: String, // Hasil analisis AI
    val status: String = "Terkirim" // Status pesan
)