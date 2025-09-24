package com.example.pinterest.data.pexelModels

data class PhotoResponse(
    val page: Int,
    val per_page: Int,
    val photos: List<Photo>,
    val total_results: Int,
    val next_page: String?
)