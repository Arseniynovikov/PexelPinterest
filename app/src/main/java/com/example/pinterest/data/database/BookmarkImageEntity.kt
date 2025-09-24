package com.example.pinterest.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookmarks_image")
data class BookmarkImageEntity(
    @PrimaryKey val id: Long,
    val url: String,
    val photographer: String,
    val photographerUrl: String,
    val srcSmall: String,
    val srcMedium: String,
    val srcLarge: String,
    val timestamp: Long = System.currentTimeMillis()
)