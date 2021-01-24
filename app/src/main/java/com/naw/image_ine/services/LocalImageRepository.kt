package com.naw.image_ine.services

import android.content.Context
import android.graphics.Bitmap
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException


class LocalImageRepository(
    private val context: Context
) : ImageRepository {

    private val gson = Gson()

    private var imageManifestDto: ImageManifestDto? = null

    override suspend fun hasManifest() = imageManifestDto != null

    override suspend fun getManifest(): ImageManifestDto {
        imageManifestDto?.let {
            return it
        }
        imageManifestDto = gson.fromJson(
            File(context.filesDir, IMAGE_MANIFEST_FILE).readText(),
            ImageManifestDto::class.java
        )
        return imageManifestDto as ImageManifestDto
    }

    override suspend fun saveManifest(manifest: ImageManifestDto): Boolean {
        imageManifestDto = manifest
        return try {
            File(context.filesDir, IMAGE_MANIFEST_FILE).writeText(
                gson.toJson(imageManifestDto)
            )
            true
        } catch (ex: IOException) {
            false
        }
    }

    override suspend fun saveImage(image: ImageDto): Boolean {
        try {
            // Save to local storage
            val fileContents = Picasso.get().load(image.downloadUrl).get()
            val stream = ByteArrayOutputStream()
            fileContents.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            val byteArray: ByteArray = stream.toByteArray()
            fileContents.recycle()
            context.openFileOutput(
                "$IMAGES_DIRECTORY/${image.id}",
                WRITE_MODE
            ).use {
                it.write(byteArray)
            }
            return true
        } catch (ex: Exception) {
            return false
        }
        // TODO: Look at cache control for later options
    }

    override suspend fun getImage(id: Int): ImageDto? {
        return imageManifestDto?.let {
            it.images[id]
        } ?: run {
            null
        }
    }

    suspend fun removeImage(id: Int): Boolean =
        context.deleteFile("$IMAGES_DIRECTORY/$id")


    suspend fun addToImageManifest(imageDao: ImageDao) {
        imageManifestDto?.images?.add(
            ImageDto(
                imageDao.id,
                imageDao.author,
                "$IMAGES_DIRECTORY/${imageDao.id}"
            )
        )
    }

    suspend fun removeFromImageManifest(id: Int) {
        imageManifestDto?.apply {
            images.drop(id)
        }
    }

    companion object {
        const val IMAGES_DIRECTORY = "images"
        const val IMAGE_MANIFEST_FILE = "image_manifest.json"
        const val WRITE_MODE = Context.MODE_PRIVATE
    }
}