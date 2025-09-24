package com.example.pinterest.data.database.mappers

import com.example.pinterest.data.database.BookmarkImageEntity
import com.example.pinterest.data.models.BookmarkImages

fun BookmarkImageEntity.toDomain(): BookmarkImages =
    BookmarkImages(
        id,
        url,
        photographer,
        photographerUrl,
        srcSmall,
        srcMedium,
        srcLarge,
        timestamp
    )

fun BookmarkImages.toEntity(): BookmarkImageEntity =
    BookmarkImageEntity(
        id,
        url,
        photographer,
        photographerUrl,
        srcSmall,
        srcMedium,
        srcLarge,
        timestamp
    )