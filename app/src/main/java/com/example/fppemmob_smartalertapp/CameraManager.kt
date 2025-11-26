package com.example.fppemmob_smartalertapp

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import java.io.File
import java.text.SimpleDateFormat
import java.util.Locale

/**
 * Kelas helper untuk mengelola semua logika yang berhubungan dengan Kamera menggunakan CameraX.
 * Dirancang untuk mengambil foto secara programatik (tanpa menampilkan preview di UI).
 *
 * @param context Konteks aplikasi.
 * @param lifecycleOwner Siklus hidup dari komponen yang memanggil (biasanya Activity/Fragment),
 *                       penting agar CameraX tahu kapan harus memulai dan berhenti.
 */
class CameraManager(
    private val context: Context,
    private val lifecycleOwner: LifecycleOwner
) {
    // Objek ImageCapture adalah "use case" utama dari CameraX untuk mengambil foto.
    private var imageCapture: ImageCapture? = null
    private val cameraProviderFuture = ProcessCameraProvider.getInstance(context)

    /**
     * Fungsi untuk memulai setup kamera.
     * Harus dipanggil terlebih dahulu sebelum mengambil foto.
     */
    fun startCamera() {
        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()
            bindCameraUseCase(cameraProvider)
        }, ContextCompat.getMainExecutor(context))
    }

    private fun bindCameraUseCase(cameraProvider: ProcessCameraProvider) {
        // Membuat instance ImageCapture dengan mode kualitas seimbang (cepat).
        imageCapture = ImageCapture.Builder()
            .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
            .build()

        // Memilih kamera belakang sebagai default.
        val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

        try {
            // Unbind semua use case sebelumnya untuk memastikan state bersih.
            cameraProvider.unbindAll()

            // Bind use case ImageCapture ke lifecycle.
            // PENTING: Karena kita TIDAK me-bind 'Preview', tidak akan ada yang ditampilkan di UI.
            cameraProvider.bindToLifecycle(
                lifecycleOwner,
                cameraSelector,
                imageCapture
            )
            Log.d("CameraManager", "Camera use case binding successful.")
        } catch (exc: Exception) {
            Log.e("CameraManager", "Use case binding failed", exc)
        }
    }

    /**
     * Fungsi utama untuk mengambil foto.
     * Hasilnya (lokasi file gambar) akan dikirim melalui callback 'onPhotoTaken'.
     *
     * @param onPhotoTaken Lambda function yang dipanggil dengan hasil Uri foto.
     *                     Akan berisi Uri jika berhasil, atau null jika gagal.
     */
    fun takePhoto(onPhotoTaken: (Uri?) -> Unit) {
        val imageCapture = imageCapture ?: run {
            Log.e("CameraManager", "ImageCapture is not initialized. Cannot take photo.")
            onPhotoTaken(null)
            return
        }

        // Membuat file unik untuk menyimpan gambar.
        val photoFile = createFile()
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        // Mengambil gambar! Ini adalah proses asynchronous.
        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(context),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    val savedUri = output.savedUri ?: Uri.fromFile(photoFile)
                    Log.d("CameraManager", "Photo capture succeeded: $savedUri")
                    // Jika berhasil, panggil callback dengan Uri dari file yang disimpan.
                    onPhotoTaken(savedUri)
                }

                override fun onError(exc: ImageCaptureException) {
                    Log.e("CameraManager", "Photo capture failed: ${exc.message}", exc)
                    // Jika gagal, panggil callback dengan hasil null.
                    onPhotoTaken(null)
                }
            }
        )
    }

    /**
     * Helper function untuk membuat file gambar dengan nama unik berdasarkan timestamp.
     */
    private fun createFile(): File {
        val outputDirectory = context.externalCacheDir ?: context.filesDir
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(System.currentTimeMillis())
        return File(outputDirectory, "IMG_${timeStamp}.jpg")
    }
}