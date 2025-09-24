package com.example.pinterest.data.pexelModels

data class Photo(
    val id: Long,
    val width: Int,
    val height: Int,
    val url: String,
    val photographer: String,
    val photographer_url: String,
    val photographer_id: Long,
    val avg_color: String?,
    val src: Src,
    val liked: Boolean
)