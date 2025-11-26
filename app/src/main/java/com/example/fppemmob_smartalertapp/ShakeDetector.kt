package com.example.fppemmob_smartalertapp

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import kotlin.math.sqrt

/**
 * Kelas ini bertanggung jawab untuk mendeteksi guncangan perangkat (shake event)
 * menggunakan sensor Akselerometer.
 *
 * Cara kerjanya:
 * 1. Mendengarkan perubahan data dari sensor Akselerometer.
 * 2. Menghitung percepatan total perangkat.
 * 3. Jika percepatan melebihi ambang batas (threshold) tertentu, maka dianggap sebagai guncangan.
 *
 * @param context Konteks aplikasi, dibutuhkan untuk mengakses SensorManager.
 * @param onShake Lambda function yang akan dipanggil setiap kali guncangan terdeteksi.
 */
class ShakeDetector(
    private val context: Context,
    private val onShake: () -> Unit // Ini adalah "callback" yang akan kita panggil
) : SensorEventListener {

    // SensorManager adalah kelas sistem Android untuk berinteraksi dengan sensor
    private val sensorManager: SensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    // Mendapatkan sensor Akselerometer dari perangkat
    private val accelerometer: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

    // Variabel untuk menyimpan data percepatan terakhir untuk perbandingan
    private var lastAcceleration = SensorManager.GRAVITY_EARTH
    private var lastShakeTime: Long = 0

    companion object {
        // Ambang batas kekuatan guncangan. Angka ini bisa diubah untuk mengatur sensitivitas.
        // Semakin TINGGI angkanya, semakin KERAS guncangan yang dibutuhkan.
        private const val SHAKE_THRESHOLD = 15.0f

        // Jeda waktu antar guncangan (dalam milidetik) untuk mencegah deteksi berulang kali.
        private const val SHAKE_TIMEOUT = 500
    }

    /**
     * Fungsi untuk mulai mendengarkan sensor.
     * Harus dipanggil saat Fragment/Activity dalam kondisi Resumed.
     */
    fun start() {
        accelerometer?.also {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_UI)
        }
    }

    /**
     * Fungsi untuk berhenti mendengarkan sensor.
     * Sangat penting untuk dipanggil saat Fragment/Activity dalam kondisi Paused
     * untuk menghemat baterai.
     */
    fun stop() {
        sensorManager.unregisterListener(this)
    }

    /**
     * Fungsi ini dipanggil oleh sistem setiap kali ada data baru dari sensor.
     * Di sinilah logika deteksi guncangan utama berada.
     */
    override fun onSensorChanged(event: SensorEvent?) {
        if (event == null) return

        if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
            val x = event.values[0]
            val y = event.values[1]
            val z = event.values[2]

            // Menghitung percepatan saat ini relatif terhadap percepatan terakhir
            val currentAcceleration = sqrt((x * x + y * y + z * z).toDouble()).toFloat()
            val delta = currentAcceleration - lastAcceleration
            lastAcceleration = currentAcceleration

            // Memeriksa apakah percepatan melewati ambang batas
            if (delta > SHAKE_THRESHOLD) {
                val now = System.currentTimeMillis()
                // Memeriksa apakah sudah cukup waktu berlalu sejak guncangan terakhir
                if (now - lastShakeTime > SHAKE_TIMEOUT) {
                    lastShakeTime = now
                    // Jika semua kondisi terpenuhi, panggil callback onShake()
                    onShake()
                }
            }
        }
    }

    /**
     * Fungsi ini dipanggil saat akurasi sensor berubah.
     * Untuk kasus ini, kita tidak perlu melakukan apa-apa.
     */
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Tidak perlu implementasi
    }
}