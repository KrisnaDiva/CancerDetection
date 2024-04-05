package com.dicoding.asclepius.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history")
data class History(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,

    @ColumnInfo(name = "prediction")
    var prediction: String = "",

    @ColumnInfo(name = "score")
    var score: String = "",

    @ColumnInfo(name = "image_url")
    var imageUrl: String? = null,

    @ColumnInfo(name = "created_at")
    var createdAt: String = ""
)