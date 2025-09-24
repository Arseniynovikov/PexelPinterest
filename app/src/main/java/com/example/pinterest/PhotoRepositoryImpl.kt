package com.example.pinterest

import com.example.pinterest.data.pexelModels.Photo
import com.example.pinterest.pexelApi.PexelApi
import javax.inject.Inject

class PhotoRepositoryImpl @Inject constructor(
    private val api: PexelApi
) : PhotoRepository {
    override suspend fun getCurated(page: Int, perPage: Int) =
        api.getCuratedPhotos(page, perPage)

    override suspend fun search(query: String, page: Int, perPage: Int) =
        api.searchPhotos(query, page, perPage)

    override suspend fun getPhotoDetails(id: Long): Photo =
        api.getPhotoDetails(id)

}