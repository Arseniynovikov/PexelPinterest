package com.example.pinterest.repository

import com.example.pinterest.data.database.BookmarkDao
import com.example.pinterest.data.database.mappers.toDomain
import com.example.pinterest.data.database.mappers.toEntity
import com.example.pinterest.data.models.BookmarkImages
import com.example.pinterest.data.pexelModels.Photo
import com.example.pinterest.pexelApi.PexelApi
import javax.inject.Inject

class PhotoRepositoryImpl @Inject constructor(
    private val api: PexelApi,
    private val dao: BookmarkDao
) : PhotoRepository {
    override suspend fun getCurated(page: Int, perPage: Int) =
        api.getCuratedPhotos(page, perPage)

    override suspend fun search(query: String, page: Int, perPage: Int) =
        api.searchPhotos(query, page, perPage)

    override suspend fun getPhotoById(id: Long): Photo =
        api.getPhotoById(id)


    override suspend fun getBookmarks(): List<BookmarkImages> =
        dao.getBookmarks().map { it ->
            it.toDomain()
        }

    override suspend fun isBookmarked(id: Long): Boolean = dao.isBookmarked(id)

    override suspend fun insertBookmark(photo: Photo) {
        val bookmark = BookmarkImages(
            photo.id,
            photo.url,
            photo.photographer,
            photo.photographer_url,
            photo.src.small,
            photo.src.medium,
            photo.src.large,
            System.currentTimeMillis()
        )
        dao.insertBookmark(bookmark.toEntity())
    }

    override suspend fun deleteBookmark(id: Long) {
        dao.deleteBookmark(id)
    }

}