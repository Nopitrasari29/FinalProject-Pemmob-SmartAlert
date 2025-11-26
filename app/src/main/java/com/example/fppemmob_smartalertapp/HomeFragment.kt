package com.example.fppemmob_smartalertapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Temukan tombol SOS
        val btnSos: FrameLayout = view.findViewById(R.id.btn_sos)
        val btnSettings: ImageView = view.findViewById(R.id.btn_settings)

        // Logika Klik Tombol SOS
        btnSos.setOnClickListener {
            // Pindah ke halaman Alert (Countdown)
            // Pastikan action ini sudah dibuat di nav_graph.xml
            findNavController().navigate(R.id.action_homeFragment_to_alertFragment)
        }

        // Logika Klik Tombol Settings Kecil
        btnSettings.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_settingsFragment)
        }
    }
}