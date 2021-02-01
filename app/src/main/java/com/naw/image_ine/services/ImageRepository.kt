package com.naw.image_ine.services

interface ImageRepository {
    suspend fun hasManifest(): Boolean
    suspend fun getManifest(): ImageManifestDto
    suspend fun getImage(id: Int): ImageDto?

    /** Optional */
    suspend fun saveImage(image: ImageDto, imageName: String): Boolean = false
    /** Optional */
    suspend fun saveManifest(manifest: ImageManifestDto): Boolean = false
}