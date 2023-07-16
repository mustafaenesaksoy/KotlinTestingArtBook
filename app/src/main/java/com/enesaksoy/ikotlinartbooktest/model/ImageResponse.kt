package com.enesaksoy.ikotlinartbooktest.model

data class ImageResponse (
    val hits : List<ImageResult>,
    val totalHits : Int,
    val total : Int
        )