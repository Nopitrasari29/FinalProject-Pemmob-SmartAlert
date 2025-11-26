package com.example.fppemmob_smartalertapp

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat

/**
 * Foreground Service yang bertanggung jawab menjaga ShakeDetector tetap hidup
 * meskipun aplikasi ditutup atau layar dimatikan.
 */
class SensorService : Service() {

    private lateinit var shakeDetector: ShakeDetector
    private lateinit var settingsManager: SettingsManager

    override fun onCreate() {
        super.onCreate()
        settingsManager = SettingsManager(this)

        // Inisialisasi ShakeDetector di dalam Service
        shakeDetector = ShakeDetector(this) {
            // Logika saat guncangan terdeteksi di background
            Log.d("SensorService", "GUNCANGAN DARURAT TERDETEKSI DARI SERVICE!")

            // TODO: Di sinilah nanti Logic Integrator (Sye) akan memanggil:
            // 1. Buka Activity Darurat
            // 2. Mulai proses SOS

            // Untuk sekarang, kita kirim broadcast agar bisa ditangkap UI jika sedang aktif
            val intent = Intent("com.example.smartalert.SHAKE_EVENT")
            sendBroadcast(intent)
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // Cek pengaturan: Apakah user mengaktifkan fitur guncangan?
        if (settingsManager.isShakeEnabled()) {
            shakeDetector.start()
            startForeground(NOTIFICATION_ID, createNotification())
            Log.d("SensorService", "Service Started & Sensor Listening")
        } else {
            stopSelf() // Matikan service jika fitur dimatikan user
        }

        // START_STICKY artinya jika Android mematikan service ini karena kurang memori,
        // Android akan mencoba menghidupkannya kembali nanti.
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        shakeDetector.stop()
        Log.d("SensorService", "Service Destroyed & Sensor Stopped")
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null // Kita tidak perlu binding
    }

    // --- Logika Notifikasi (Wajib untuk Foreground Service) ---

    private fun createNotification(): Notification {
        val channelId = "SmartAlert_Sensor_Channel"
        val channelName = "SmartAlert Monitoring"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_LOW // Low agar tidak berisik/mengganggu
            )
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }

        return NotificationCompat.Builder(this, channelId)
            .setContentTitle("SmartAlert Aktif")
            .setContentText("Memantau guncangan untuk keamanan Anda...")
            .setSmallIcon(R.mipmap.ic_launcher) // Pastikan icon ada
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .build()
    }

    companion object {
        private const val NOTIFICATION_ID = 1
    }
}