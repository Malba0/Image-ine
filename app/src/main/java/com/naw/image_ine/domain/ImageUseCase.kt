package com.naw.image_ine.domain

import androidx.annotation.VisibleForTesting
import com.naw.image_ine.services.ImageRepository
import java.lang.Exception

/**
 * The Use Case contains all logic to
 * maintain the images and provide the ViewModel
 * consistent data.
 */
class ImageUseCase(
    private val onlineImageRepository: ImageRepository,
    private val localImageRepository: ImageRepository,
    private val randomGenerator: RandomGenerator
) {
    @VisibleForTesting
    var localImages: ArrayList<ImageBo> = arrayListOf()

    suspend fun getImages(): List<ImageBo> {
//        return localImageRepository.getManifest().images.map {
//            ImageBo(
//                it.id,
//                it.author,
//                it.downloadUrl
//            )
//        }
        return localImages
    }

    suspend fun getNewImage(): ImageBo {
        val onlineManifest = onlineImageRepository.getManifest()
        val rnd = randomGenerator.getRandom(onlineManifest.images.size)
        val image = onlineManifest.images[rnd]

        image?.let {

            val imageBo = ImageBo(
                it.id,
                it.author,
                it.downloadUrl
            )

            localImages.add(imageBo)

            return imageBo
        }
        // TODO: Even though it should be hard to cause this;
        //  it could be handled better.
        throw Exception("Local image not available!!!")
    }
}