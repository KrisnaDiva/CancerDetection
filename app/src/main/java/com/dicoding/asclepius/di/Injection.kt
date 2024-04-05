package com.dicoding.asclepius.di

import android.content.Context
import com.dicoding.asclepius.data.local.room.CancerDetectionDatabase
import com.dicoding.asclepius.repository.HistoryRepository
import java.util.concurrent.Executors

object Injection {
    fun provideRepository(context: Context): HistoryRepository {
        val database = CancerDetectionDatabase.getDatabase(context)
        val dao = database.historyDao()
        val appExecutors = Executors.newSingleThreadExecutor()
        return HistoryRepository.getInstance(dao, appExecutors)
    }
}