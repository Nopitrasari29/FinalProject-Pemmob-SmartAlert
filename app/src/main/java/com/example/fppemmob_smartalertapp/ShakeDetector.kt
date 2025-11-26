package com.example.fppemmob_smartalertapp

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import kotlin.math.sqrt

class ShakeDetector(
    private val context: Context,
    private val onShake: () -> Unit
) : SensorEventListener {

    private val sensorManager: SensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val accelerometer: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

    // --- TAMBAHAN: Inisialisasi SettingsManager ---
    private val settingsManager = SettingsManager(context)

    private var lastAcceleration = SensorManager.GRAVITY_EARTH
    private var lastShakeTime: Long = 0

    companion object {
        private const val SHAKE_TIMEOUT = 500
    }

    fun start() {
        accelerometer?.also {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_UI)
        }
    }

    fun stop() {
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event == null) return

        if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
            val x = event.values[0]
            val y = event.values[1]
            val z = event.values[2]

            val currentAcceleration = sqrt((x * x + y * y + z * z).toDouble()).toFloat()
            val delta = currentAcceleration - lastAcceleration
            lastAcceleration = currentAcceleration

            // --- UBAHAN: Ambil threshold dari SettingsManager ---
            val currentThreshold = settingsManager.getShakeSensitivity()

            // Gunakan currentThreshold, bukan angka mati
            if (delta > currentThreshold) {
                val now = System.currentTimeMillis()
                if (now - lastShakeTime > SHAKE_TIMEOUT) {
                    lastShakeTime = now
                    onShake()
                }
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Tidak perlu implementasi
    }
}