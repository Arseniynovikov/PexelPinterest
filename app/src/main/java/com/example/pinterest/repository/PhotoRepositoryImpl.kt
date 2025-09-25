package com.example.pinterest.repository

import com.example.pinterest.data.database.BookmarkDao
import com.example.pinterest.data.database.mappers.toDomain
import com.example.pinterest.data.database.mappers.toEntity
import com.example.pinterest.data.models.BookmarkImages
import com.example.pinterest.data.models.UIImage
import com.example.pinterest.data.models.toUIImage
import com.example.pinterest.pexelApi.PexelApi
import javax.inject.Inject

class PhotoRepositoryImpl @Inject constructor(
    private val api: PexelApi,
    private val dao: BookmarkDao
) : PhotoRepository {
    override suspend fun getCurated(page: Int, perPage: Int) =
        api.getCuratedPhotos(page, perPage).photos.map { it.toUIImage() }

    override suspend fun search(query: String, page: Int, perPage: Int) =
        api.searchPhotos(query, page, perPage).photos.map { it.toUIImage() }


    override suspend fun getImageById(id: Long): UIImage {
        return if(isBookmarked(id)){
            getBookmarkById(id)
        } else {
            getPhotoById(id)
        }
    }

    private suspend fun getPhotoById(id: Long): UIImage =
        api.getPhotoById(id).toUIImage()
    private suspend fun getBookmarkById(id: Long): UIImage =
        dao.getBookmarkById(id).toDomain().toUIImage()


    override suspend fun getBookmarks(): List<UIImage> =
        dao.getBookmarks().map { it ->
            it.toDomain().toUIImage()
        }

    override suspend fun isBookmarked(id: Long): Boolean = dao.isBookmarked(id)

    override suspend fun insertBookmark(image: UIImage) {
        val photo = api.getPhotoById(image.id)
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