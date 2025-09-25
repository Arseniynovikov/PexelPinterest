package com.example.pinterest.repository

import com.example.pinterest.data.models.UIImage


interface PhotoRepository {
    suspend fun getCurated(page: Int, perPage: Int): List<UIImage>
    suspend fun search(query: String, page: Int, perPage: Int): List<UIImage>

    suspend fun getImageById(id: Long): UIImage

    suspend fun getBookmarks(): List<UIImage>
    suspend fun isBookmarked(id: Long):Boolean
    suspend fun insertBookmark(image: UIImage)
    suspend fun deleteBookmark(id: Long)
}