package com.dicoding.asclepius.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding.asclepius.data.local.entity.History
import com.dicoding.asclepius.repository.HistoryRepository

class HistoryViewModel(private val historyRepository: HistoryRepository) : ViewModel() {
    fun delete(history: History) {
        historyRepository.delete(history)
    }

    fun insert(history: History) {
        historyRepository.insert(history)
    }

    fun getAll(): LiveData<List<History>> {
        return historyRepository.getAll()
    }

    fun deleteAll() {
        historyRepository.deleteAll()
    }
}
