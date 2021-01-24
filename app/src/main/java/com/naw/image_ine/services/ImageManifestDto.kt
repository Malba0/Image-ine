package com.naw.image_ine.services

import com.google.gson.annotations.SerializedName

data class ImageManifestDto(
    @SerializedName("images")
    val images: ArrayList<ImageDto>
)