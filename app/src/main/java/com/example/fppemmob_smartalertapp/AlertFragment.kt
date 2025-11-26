package com.example.fppemmob_smartalertapp

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class AlertFragment : Fragment() {

    private var timer: CountDownTimer? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_alert, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val progressBar: ProgressBar = view.findViewById(R.id.progressBar)
        val tvCountdown: TextView = view.findViewById(R.id.tv_countdown)
        val btnCancel: Button = view.findViewById(R.id.btn_cancel)

        // Timer 5 Detik
        timer = object : CountDownTimer(5000, 100) {
            override fun onTick(millisUntilFinished: Long) {
                // Update Angka
                val secondsLeft = (millisUntilFinished / 1000) + 1
                tvCountdown.text = "${secondsLeft}s"

                // Update Lingkaran Merah
                val progress = ((millisUntilFinished.toFloat() / 5000.toFloat()) * 100).toInt()
                progressBar.progress = progress
            }

            override fun onFinish() {
                tvCountdown.text = "0s"
                progressBar.progress = 0
                // TODO: Pindah ke halaman Alert Process (Nanti)
            }
        }.start()

        // Tombol Cancel
        btnCancel.setOnClickListener {
            timer?.cancel()
            findNavController().popBackStack() // Balik ke Home
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        timer?.cancel() // Matikan timer kalau keluar halaman
    }
}