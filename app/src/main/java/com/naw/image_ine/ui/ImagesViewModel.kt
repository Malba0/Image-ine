package com.naw.image_ine.ui

import androidx.lifecycle.*
import com.naw.image_ine.domain.ImageUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class ImagesViewModel() : ViewModel() {

    @Inject
    lateinit var imageUseCase: ImageUseCase

    //region LiveDate = Images
    private val images: MutableLiveData<List<ImageUio>> by lazy {
        MutableLiveData<List<ImageUio>>()
    }

    fun getImages(): LiveData<List<ImageUio>> = images
    //endregion

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