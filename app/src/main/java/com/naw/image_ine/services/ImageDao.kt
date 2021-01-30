package com.naw.image_ine.services

import com.google.gson.annotations.SerializedName

data class ImageDao(

    @SerializedName("id")
    val id: Int,

    @SerializedName("author")
    val author: String,

    @SerializedName("download_url")
    val downloadUrl: String
)

/**
REFERENCE:

[{
"id": "0",
"author": "Alejandro Escamilla",
"width": 5616,
"height": 3744,
"url": "https://unsplash.com/photos/yC-Yzbqy7PY",
"download_url": "https://picsum.photos/id/0/5616/3744"
},

 */