package com.example.fppemmob_smartalertapp

import android.content.Context
import android.content.SharedPreferences

/**
 * Kelas helper untuk menyimpan pengaturan aplikasi menggunakan SharedPreferences.
 * Menyimpan data sederhana seperti preferensi sensitivitas sensor.
 */
class SettingsManager(context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences("smartalert_prefs", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_SHAKE_SENSITIVITY = "shake_sensitivity"

        // Nilai threshold (Semakin KECIL angkanya, semakin SENSITIF/Mudah terpicu)
        // High Sensitivity = Guncangan pelan pun bunyi
        const val SENSITIVITY_HIGH = 11.0f
        const val SENSITIVITY_MEDIUM = 15.0f
        const val SENSITIVITY_LOW = 20.0f
    }

    /**
     * Menyimpan level sensitivitas yang dipilih user.
     */
    fun setShakeSensitivity(level: Float) {
        prefs.edit().putFloat(KEY_SHAKE_SENSITIVITY, level).apply()
    }

    /**
     * Mengambil level sensitivitas saat ini. Default-nya MEDIUM.
     */
    fun getShakeSensitivity(): Float {
        return prefs.getFloat(KEY_SHAKE_SENSITIVITY, SENSITIVITY_MEDIUM)
    }
}