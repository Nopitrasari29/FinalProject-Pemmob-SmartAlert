package com.example.fppemmob_smartalertapp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "alert_history")
data class AlertHistoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val timestamp: Long, // Waktu kejadian (disimpan dalam milidetik)
    val location: String, // Koordinat atau alamat singkat (misal: "-6.200, 106.816")
    val aiAnalysisResult: String, // Hasil analisis AI (misal: "Mobil, Malam Hari")
    val status: String = "Terkirim" // Status pesan (Terkirim/Gagal)
)