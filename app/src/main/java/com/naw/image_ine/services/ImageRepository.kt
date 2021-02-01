package com.naw.image_ine.services

interface ImageRepository {
    suspend fun getManifest(): ImageManifestDto
    /** Optional */
    suspend fun saveManifest(manifest: ImageManifestDto): Boolean = false
}