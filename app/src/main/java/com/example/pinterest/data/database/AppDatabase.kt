package com.example.pinterest.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [BookmarkImageEntity::class], exportSchema = false, version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun bookmarkDao(): BookmarkDao
}