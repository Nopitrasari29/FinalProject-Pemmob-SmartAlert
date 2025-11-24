package com.example.fppemmob_smartalertapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast

class SettingsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Temukan item menu berdasarkan ID LinearLayout-nya
        val menuAccount: LinearLayout = view.findViewById(R.id.menuAccount)
        val menuSecurity: LinearLayout = view.findViewById(R.id.menuSecurity)
        val menuAbout: LinearLayout = view.findViewById(R.id.menuAbout)

        // Berikan listener klik
        menuAccount.setOnClickListener {
            Toast.makeText(context, "Buka Pengaturan Akun", Toast.LENGTH_SHORT).show()
        }

        menuSecurity.setOnClickListener {
            Toast.makeText(context, "Atur Sensitivitas Sensor", Toast.LENGTH_SHORT).show()
        }

        menuAbout.setOnClickListener {
            Toast.makeText(context, "Tentang SmartAlert v1.0", Toast.LENGTH_SHORT).show()
        }
    }
}