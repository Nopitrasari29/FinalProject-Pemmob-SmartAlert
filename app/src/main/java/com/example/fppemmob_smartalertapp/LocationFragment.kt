package com.example.fppemmob_smartalertapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
// PENTING: Pastikan baris di bawah ini ada!
import com.example.fppemmob_smartalertapp.R

class LocationFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        // Kalau R.layout.fragment_location masih merah, coba Rebuild Project
        return inflater.inflate(R.layout.fragment_location, container, false)
    }
}