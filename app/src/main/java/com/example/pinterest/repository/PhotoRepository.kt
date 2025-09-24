package com.example.pinterest.repository

import com.example.pinterest.data.models.BookmarkImages
import com.example.pinterest.data.pexelModels.Photo
import com.example.pinterest.data.pexelModels.PhotoResponse

interface PhotoRepository {
    suspend fun getCurated(page: Int, perPage: Int): PhotoResponse
    suspend fun search(query: String, page: Int, perPage: Int): PhotoResponse
    suspend fun getPhotoById(id: Long): Photo

    suspend fun getBookmarks(): List<BookmarkImages>
    suspend fun isBookmarked(id: Long):Boolean
    suspend fun insertBookmark(photo: Photo)
    suspend fun deleteBookmark(id: Long)
}