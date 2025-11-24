package com.example.fppemmob_smartalertapp

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng

class HomeFragment : Fragment(), OnMapReadyCallback {

    private lateinit var map: GoogleMap
    private lateinit var rvContactsMap: RecyclerView
    private lateinit var contactsAdapter: ContactsMapAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1. Inisialisasi Maps
        val mapFragment = childFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        // 2. Setup RecyclerView Kontak
        rvContactsMap = view.findViewById(R.id.rvContactsMap)
        setupContactsList()
    }

    private fun setupContactsList() {
        // --- DUMMY DATA --- (Ganti nomor HP dengan yang asli untuk tes)
        val dummyContacts = listOf(
            ContactModel(1, "Ayah", "08123456789", "Siaga"),
            ContactModel(2, "Ibu", "08987654321", "Di Kantor"),
            ContactModel(3, "Polsek Terdekat", "110", "Layanan Darurat")
        )

        // Inisialisasi Adapter dengan logika klik tombol panggil
        contactsAdapter = ContactsMapAdapter(dummyContacts) { contactClicked ->
            // --- LOGIKA SAAT TOMBOL TELEPON DIKLIK ---

            // 1. Getarkan HP
            triggerHapticFeedback()
            // 2. Tampilkan Toast
            Toast.makeText(context, "Memanggil ${contactClicked.name}...", Toast.LENGTH_SHORT).show()
            // 3. Buka Dialer (Aplikasi Telepon)
            initiateCallIntent(contactClicked.phoneNumber)
        }

        rvContactsMap.adapter = contactsAdapter
    }

    // --- FUNGSI UTILITAS 1: GETARAN (Haptic Feedback) ---
    private fun triggerHapticFeedback() {
        val context = requireContext()
        val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            vibratorManager.defaultVibrator
        } else {
            @Suppress("DEPRECATION")
            context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        }

        if (vibrator.hasVibrator()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.EFFECT_HEAVY_CLICK))
            } else {
                @Suppress("DEPRECATION")
                vibrator.vibrate(100)
            }
        }
    }

    // --- FUNGSI UTILITAS 2: INTENT TELEPON ---
    private fun initiateCallIntent(phoneNumber: String) {
        // Menggunakan ACTION_DIAL (lebih aman, membuka dialer)
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$phoneNumber")
        }
        if (intent.resolveActivity(requireActivity().packageManager) != null) {
            startActivity(intent)
        } else {
            Toast.makeText(context, "Tidak ada aplikasi telepon ditemukan.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        map.uiSettings.isMapToolbarEnabled = false
        map.uiSettings.isZoomControlsEnabled = false
        // Placeholder lokasi Jakarta
        val defaultLocation = LatLng(-6.175392, 106.827153)
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 15f))
    }
}