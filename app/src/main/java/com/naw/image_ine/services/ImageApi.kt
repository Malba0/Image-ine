package com.naw.image_ine.services

import retrofit2.http.GET


interface ImageApi {
    @GET("/v2/list")
    suspend fun fetchImages(): Array<ImageDao>
}