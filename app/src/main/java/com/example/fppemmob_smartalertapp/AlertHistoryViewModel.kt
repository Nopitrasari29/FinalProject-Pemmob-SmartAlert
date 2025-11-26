package com.example.fppemmob_smartalertapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class AlertHistoryViewModel(private val repository: AlertHistoryRepository) : ViewModel() {

    val allHistory: LiveData<List<AlertHistoryEntity>> = repository.allHistory.asLiveData()

    fun insert(history: AlertHistoryEntity) = viewModelScope.launch {
        repository.insert(history)
    }

    fun clearAll() = viewModelScope.launch {
        repository.clearAll()
    }
}

class AlertHistoryViewModelFactory(private val repository: AlertHistoryRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AlertHistoryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AlertHistoryViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}