package com.naw.image_ine.services

import com.google.gson.annotations.SerializedName

data class ImageDto(
    @SerializedName("id")
    val id: Int,

    @SerializedName("author")
    val author: String,

    @SerializedName("download_url")
    val downloadUrl: String
)