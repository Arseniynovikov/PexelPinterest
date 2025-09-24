package com.example.pinterest

import com.example.pinterest.data.pexelModels.Photo
import com.example.pinterest.data.pexelModels.PhotoResponse

interface PhotoRepository {
    suspend fun getCurated(page: Int, perPage: Int): PhotoResponse
    suspend fun search(query: String, page: Int, perPage: Int): PhotoResponse
    suspend fun getPhotoDetails(id: Long): Photo
}