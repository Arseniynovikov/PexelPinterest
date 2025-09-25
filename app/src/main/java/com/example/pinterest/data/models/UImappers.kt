package com.example.pinterest.data.models

import com.example.pinterest.data.pexelModels.Photo

fun BookmarkImages.toUIImage(): UIImage =
    UIImage(
        id,
        photographer,
        srcMedium,
        srcLarge
    )

fun Photo.toUIImage():UIImage =
    UIImage(
        id,
        photographer,
        src.medium,
        src.large
    )