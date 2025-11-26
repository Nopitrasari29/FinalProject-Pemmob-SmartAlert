package com.example.fppemmob_smartalertapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class AlertHistoryViewModel(private val repository: AlertHistoryRepository) : ViewModel() {

    // Data yang akan diamati oleh UI (HistoryFragment)
    val allHistory: LiveData<List<AlertHistoryEntity>> = repository.allHistory.asLiveData()

    // Fungsi untuk menyimpan riwayat (dipanggil saat SOS selesai dikirim)
    fun insert(history: AlertHistoryEntity) = viewModelScope.launch {
        repository.insert(history)
    }

    // Fungsi untuk menghapus riwayat
    fun clearAll() = viewModelScope.launch {
        repository.clearAll()
    }
}

// Factory untuk membuat ViewModel ini
class AlertHistoryViewModelFactory(private val repository: AlertHistoryRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AlertHistoryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AlertHistoryViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}