package com.naw.image_ine.services

import retrofit2.Retrofit
import kotlin.random.Random

class OnlineImageRepository: ImageRepository {

    private var manifest: ImageManifestDto? = null


    override suspend fun hasManifest(): Boolean = manifest != null

    override suspend fun getManifest(): ImageManifestDto {

        val retrofit = Retrofit.Builder()
            .baseUrl("https://picsum.photos/")
            .build()
        val service = retrofit.create(ImageApi::class.java)
        val onlineImages = service.fetchImages().images

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

    override suspend fun getImage(id: Int): ImageDto? {
        manifest?.let {
            return it.images[Random.nextInt(0, it.images.size-1)]
        } ?: run {
            return null
        }
    }
}