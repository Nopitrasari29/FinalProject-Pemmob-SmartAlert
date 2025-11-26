package com.example.fppemmob_smartalertapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class AlertProcessFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate layout untuk fragment ini
        return inflater.inflate(R.layout.fragment_alert_process, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Tombol Back di kiri atas
        val btnBack: ImageView = view.findViewById(R.id.btn_back)

        btnBack.setOnClickListener {
            // Kembali ke halaman Home (Peta) agar user keluar dari mode darurat
            // Menggunakan popBackStack ke locationFragment agar tumpukan halaman bersih
            findNavController().popBackStack(R.id.locationFragment, false)
        }
    }
}