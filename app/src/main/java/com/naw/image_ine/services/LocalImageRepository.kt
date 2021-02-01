package com.naw.image_ine.services

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException


class LocalImageRepository(
    private val context: Context
) : ImageRepository {

//    init {  // TODO: Called multiple times; maybe because of DI?
//        // Check if manifest file is available
//        GlobalScope.launch {
//            if (File(context.filesDir, IMAGE_MANIFEST_FILE).exists()) {
//                getManifest()
//            } else {
//                saveManifest(ImageManifestDto(arrayListOf()))
//            }
//        }
//    }

    private val gson = Gson()

    private var imageManifestDto: ImageManifestDto = ImageManifestDto(arrayListOf())

    override suspend fun hasManifest() = imageManifestDto.images.isNotEmpty()

    override suspend fun getManifest(): ImageManifestDto {

        imageManifestDto = try {

            val file = File(context.filesDir, IMAGE_MANIFEST_FILE)
            return if (file.exists()) {
                val text = file.readText()
                if (text.isNotBlank()) {
                    gson.fromJson(text, ImageManifestDto::class.java)
                } else {
                    ImageManifestDto(arrayListOf())
                }
            } else {
                ImageManifestDto(arrayListOf())
            }
        } catch (ex: Exception) {
            Log.e("LocalImageRepository", "Empty Local Manifest!", ex)
            ImageManifestDto(arrayListOf())
        }
        return imageManifestDto
    }

    override suspend fun saveManifest(manifest: ImageManifestDto): Boolean = withContext(Dispatchers.IO) {
        imageManifestDto = manifest
        return@withContext try {
            Log.d("LocalImageRepo", "Saving file=${context.filesDir}/$IMAGE_MANIFEST_FILE")
            val file = File(context.filesDir, IMAGE_MANIFEST_FILE)
            if (!file.exists()) {
                file.createNewFile()
            }
            File(context.filesDir, IMAGE_MANIFEST_FILE).writeText(
                gson.toJson(imageManifestDto)
            )
            true
        } catch (ex: IOException) {
            false
        }
    }


    // TODO: remove
    override suspend fun saveImage(
        image: ImageDto,
        imageName: String
    ): Boolean = withContext(Dispatchers.IO) {
        try {
                val fileContents = Picasso.get().load(image.downloadUrl).get()
                val stream = ByteArrayOutputStream()
                fileContents.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                val byteArray: ByteArray = stream.toByteArray()
                fileContents.recycle()
                context.openFileOutput(
                    "${context.filesDir}$imageName",
                    WRITE_MODE
                ).use {
                    it.write(byteArray)
                }
                return@withContext true

        } catch (ex: Exception) {
            Log.e("LocalImageRepo", ex.localizedMessage, ex)
            return@withContext false
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


    suspend fun addToImageManifest(imageDao: ImageDto) {
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