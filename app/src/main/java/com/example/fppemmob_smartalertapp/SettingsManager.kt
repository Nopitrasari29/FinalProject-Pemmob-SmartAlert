package com.example.fppemmob_smartalertapp

import android.content.Context
import android.content.SharedPreferences

class SettingsManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("smartalert_prefs", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_SHAKE_SENSITIVITY = "shake_sensitivity"
        private const val KEY_IS_SHAKE_ENABLED = "is_shake_enabled" // Constant baru
        const val SENSITIVITY_MEDIUM = 15.0f
    }

    fun getShakeSensitivity(): Float {
        return prefs.getFloat(KEY_SHAKE_SENSITIVITY, SENSITIVITY_MEDIUM)
    }

    // --- TAMBAHKAN INI AGAR ERROR HILANG ---
    fun isShakeEnabled(): Boolean {
        // Default true (fitur nyala)
        return prefs.getBoolean(KEY_IS_SHAKE_ENABLED, true)
    }
}