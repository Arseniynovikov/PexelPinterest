package com.example.pinterest.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface BookmarkDao {
    @Query("SELECT * FROM bookmarks_image ORDER BY timestamp")
    suspend fun getBookmarks(): List<BookmarkImageEntity>

    @Query("SELECT EXISTS(SELECT 1 FROM bookmarks_image WHERE id = :id)")
    suspend fun isBookmarked(id: Long): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookmark(bookmark: BookmarkImageEntity)

    @Query("DELETE FROM bookmarks_image WHERE id = :id")
    suspend fun deleteBookmark(id: Long)
}