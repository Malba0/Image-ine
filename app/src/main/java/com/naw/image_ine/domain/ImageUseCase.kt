package com.naw.image_ine.domain

import androidx.annotation.VisibleForTesting
import com.naw.image_ine.services.ImageDto
import com.naw.image_ine.services.ImageManifestDto
import com.naw.image_ine.services.ImageRepository

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

    @VisibleForTesting
    var onlineImages: ArrayList<ImageDto> = arrayListOf()

    suspend fun getImages(): List<ImageBo> {

        initOnline()
        initLocal()

        return localImages
    }

    suspend fun getNewImage(): ImageBo {

        initOnline()

        val image = getRandomImage()
        localImages.add(image)
        onlineImages.remove(onlineImages.find { it.id == image.id })

        return image
    }

    suspend fun saveLocalImages() {
        initLocal()
        localImageRepository.saveManifest(ImageManifestDto(
            localImages.map { bo2Dto(it) } as ArrayList<ImageDto>
        ))
    }

    private suspend fun initOnline() {
        if (!isOnlineInitialised()) {
            onlineImages = onlineImageRepository.getManifest().images
        }
    }

    private suspend fun initLocal() {
        if (!isLocalInitialised()) {
            val localImg = localImageRepository.getManifest().images
            localImages = localImg.map {
                dto2Bo(it)
            } as ArrayList<ImageBo>
        }
    }

    /** Assume that if the persistent online images are empty. */
    private fun isOnlineInitialised(): Boolean {
        return onlineImages.isNotEmpty()
    }

    private fun isLocalInitialised(): Boolean {
        return localImages.isNotEmpty()
    }

    private suspend fun getRandomImage(): ImageBo {
        val rnd = randomGenerator.getRandom(onlineImages.size)
        return dto2Bo(onlineImages[rnd])
    }

    private fun dto2Bo(dto: ImageDto) = ImageBo(dto.id, dto.author, dto.downloadUrl)
    private fun bo2Dto(bo: ImageBo) = ImageDto(bo.id, bo.author, bo.downloadUrl)
}