package com.dicoding.asclepius.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.asclepius.di.Injection
import com.dicoding.asclepius.repository.HistoryRepository
import com.dicoding.asclepius.viewmodel.HistoryViewModel

class HistoryViewModelFactory private constructor(
    private val historyRepository: HistoryRepository,
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HistoryViewModel::class.java) -> {
                HistoryViewModel(historyRepository) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }

    companion object {
        @Volatile
        private var instance: HistoryViewModelFactory? = null
        fun getInstance(context: Context): HistoryViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: HistoryViewModelFactory(Injection.provideRepository(context))
            }.also { instance = it }
    }
}
