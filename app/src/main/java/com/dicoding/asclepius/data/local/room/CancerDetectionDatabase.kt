package com.dicoding.asclepius.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dicoding.asclepius.data.local.entity.History

@Database(entities = [History::class], version = 1, exportSchema = false)
abstract class CancerDetectionDatabase : RoomDatabase() {
    abstract fun historyDao(): HistoryDao

    companion object {
        @Volatile
        private var INSTANCE: CancerDetectionDatabase? = null
        fun getDatabase(context: Context): CancerDetectionDatabase =
            INSTANCE ?: synchronized(CancerDetectionDatabase::class.java) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    CancerDetectionDatabase::class.java, "cancer_detection_database"
                ).build()
            }
    }
}