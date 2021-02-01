package com.naw.image_ine.services

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.IOException


class LocalImageRepository(
    private val context: Context
) : ImageRepository {

    private val gson = Gson()

    private var imageManifestDto: ImageManifestDto = ImageManifestDto(arrayListOf())

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

    companion object {
        const val IMAGE_MANIFEST_FILE = "image_manifest.json"
    }
}