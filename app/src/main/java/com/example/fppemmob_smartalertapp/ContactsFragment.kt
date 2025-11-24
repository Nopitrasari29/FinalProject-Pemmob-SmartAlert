package com.example.fppemmob_smartalertapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton

class ContactsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_contacts, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnAddContact: ExtendedFloatingActionButton = view.findViewById(R.id.btnAddContact)

        btnAddContact.setOnClickListener {
            // TUGAS FRONTEND: Menyiapkan listener klik.
            Toast.makeText(context, "Tombol TAMBAH KONTAK diklik. (Menunggu implementasi Database)", Toast.LENGTH_SHORT).show()
        }
    }
}