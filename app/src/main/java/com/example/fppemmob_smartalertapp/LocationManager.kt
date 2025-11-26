package com.example.fppemmob_smartalertapp

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.util.Log
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority

/**
 * Kelas helper untuk mengelola semua logika yang berhubungan dengan GPS.
 * Menyederhanakan proses untuk mendapatkan lokasi pengguna saat ini.
 *
 * @param context Konteks aplikasi, dibutuhkan untuk mengakses layanan lokasi dari sistem.
 */
class LocationManager(private val context: Context) {

    // Klien utama dari Google Play Services untuk berinteraksi dengan layanan lokasi.
    // 'by lazy' berarti objek ini hanya akan dibuat saat pertama kali diakses.
    private val fusedLocationClient: FusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(context)
    }

    /**
     * Fungsi utama untuk meminta lokasi pengguna saat ini.
     * Ini adalah operasi asynchronous (hasilnya tidak langsung tersedia). Hasilnya akan dikirimkan
     * melalui sebuah callback (lambda function) 'onLocationResult'.
     *
     * @param onLocationResult Lambda function yang akan dipanggil dengan hasil lokasi.
     *                         Parameter callback ini adalah:
     *                         - Boolean: 'isSuccess' -> true jika lokasi berhasil didapat.
     *                         - Location?: 'location' -> Objek Location jika berhasil, atau null jika gagal.
     */
    fun getCurrentLocation(onLocationResult: (isSuccess: Boolean, location: Location?) -> Unit) {
        // PERIKSA IZIN: Ini adalah langkah keamanan yang wajib. Aplikasi akan crash jika mencoba
        // mengakses lokasi tanpa izin yang telah diberikan oleh pengguna.
        val hasFineLocationPermission = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val hasCoarseLocationPermission = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        // Jika salah satu dari izin lokasi (halus atau kasar) tidak diberikan, hentikan proses.
        if (!hasFineLocationPermission && !hasCoarseLocationPermission) {
            Log.e("LocationManager", "Izin lokasi tidak diberikan oleh pengguna.")
            // Panggil callback dengan status gagal dan lokasi null.
            onLocationResult(false, null)
            return
        }

        // Jika izin sudah ada, kita meminta lokasi saat ini dari FusedLocationProviderClient.
        // Priority.PRIORITY_HIGH_ACCURACY memberitahu sistem untuk menggunakan GPS untuk akurasi terbaik.
        fusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null)
            .addOnSuccessListener { location: Location? ->
                // Jika permintaan berhasil, panggil callback dengan status sukses
                // dan objek location yang didapat (bisa jadi null jika lokasi tidak tersedia).
                Log.d("LocationManager", "Lokasi berhasil didapatkan: $location")
                onLocationResult(true, location)
            }
            .addOnFailureListener { exception ->
                // Jika permintaan gagal (misalnya, GPS mati), panggil callback dengan status gagal.
                Log.e("LocationManager", "Gagal mendapatkan lokasi.", exception)
                onLocationResult(false, null)
            }
    }
}