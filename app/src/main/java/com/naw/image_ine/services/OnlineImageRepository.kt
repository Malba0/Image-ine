package com.naw.image_ine.services

import androidx.annotation.VisibleForTesting
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class OnlineImageRepository: ImageRepository {

    @VisibleForTesting
    var manifest: ImageManifestDto? = null

    override suspend fun getManifest(): ImageManifestDto {

        if (manifest != null) return manifest as ImageManifestDto
// TODO: Interface it out so that it can be tested
        val retrofit = Retrofit.Builder()
            .baseUrl("https://picsum.photos/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(ImageApi::class.java)
        val onlineImages = service.fetchImages()

        manifest = ImageManifestDto(
            onlineImages.map {imageDao ->
                ImageDto(
                    imageDao.id,
                    imageDao.author,
                    imageDao.downloadUrl
                )
            } as ArrayList<ImageDto>
        )
        return manifest as ImageManifestDto
    }
}