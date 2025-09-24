package com.example.pinterest.pexelApi

import com.example.pinterest.data.pexelModels.Photo
import com.example.pinterest.data.pexelModels.PhotoResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PexelApi {
    @GET("v1/curated")
    suspend fun getCuratedPhotos(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): PhotoResponse

    @GET("v1/search")
    suspend fun searchPhotos(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): PhotoResponse

    @GET("v1/photos/{id}")
    suspend fun getPhotoDetails(@Path("id") id: Long): Photo
}