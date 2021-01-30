package com.naw.image_ine.domain

import com.naw.image_ine.services.OnlineImageRepository
import com.naw.image_ine.services.LocalImageRepository
import java.lang.Exception
import javax.inject.Inject
import kotlin.random.Random

/**
 * The Use Case contains all logic to
 * maintain the images and provide the ViewModel
 * consistent data.
 */
class ImageUseCase(
    private val onlineImageRepository: OnlineImageRepository,
    private val localImageRepository: LocalImageRepository
) {

    suspend fun getImages(): List<ImageBo> {
        return localImageRepository.getManifest().images.map {
            ImageBo(
                it.id,
                it.author,
                it.downloadUrl
            )
        }
    }

    suspend fun getNewImage(): ImageBo {
        val onlineManifest = onlineImageRepository.getManifest()
        val rnd = Random.nextInt(onlineManifest.images.size-1)
        val image = onlineImageRepository.getImage(onlineManifest.images[rnd].id)
        image?.let {
            localImageRepository.addToImageManifest(it)
            localImageRepository.saveManifest(localImageRepository.getManifest())
            localImageRepository.saveImage(it)
            return ImageBo(
                image.id,
                image.author,
                image.downloadUrl
            )
        }
        // TODO: Even though it should be hard to cause this;
        //  it could be handled better.
        throw Exception("Local image not available!!!")
    }
}