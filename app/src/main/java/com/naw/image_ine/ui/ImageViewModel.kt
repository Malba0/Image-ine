package com.naw.image_ine.ui

import androidx.lifecycle.*
import com.naw.image_ine.domain.ImageUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class ImageViewModel(): ViewModel() {

    @Inject
    lateinit var imageUseCase: ImageUseCase

    private val images: MutableLiveData<List<ImageUio>> by lazy {
        MutableLiveData<List<ImageUio>>()
    }

    fun getImages() {
        viewModelScope.launch {
            updateImages()
        }
    }

    fun getNewImage() {
        viewModelScope.launch {
            imageUseCase.getNewImage()
            updateImages()
        }
    }

    private suspend fun updateImages() {
        images.value = imageUseCase.getImages().map {
            ImageUio(
                it.id,
                it.author,
                it.downloadUrl
            )
        }
    }
    /** LATER: Save image order; i.e. new manifest */
}