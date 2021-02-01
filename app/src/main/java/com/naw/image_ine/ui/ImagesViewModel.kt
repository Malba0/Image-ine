package com.naw.image_ine.ui

import android.app.Application
import androidx.lifecycle.*
import com.naw.image_ine.domain.ImageUseCase
import com.naw.image_ine.domain.ImageIneRandomGenerator
import com.naw.image_ine.services.LocalImageRepository
import com.naw.image_ine.services.OnlineImageRepository
import kotlinx.coroutines.launch

class ImagesViewModel(application: Application) : AndroidViewModel(application) {

//    @Inject
//    lateinit var imageUseCase: ImageUseCase
    private val imageUseCase = ImageUseCase(
        OnlineImageRepository(),
        LocalImageRepository(application.applicationContext),
        ImageIneRandomGenerator()
    )

    //region LiveDate = Images
    private val images: MutableLiveData<List<ImageUio>> by lazy {
        MutableLiveData<List<ImageUio>>()
    }

    fun getImages(): LiveData<List<ImageUio>> = images
    //endregion

    override fun onCleared() {
        viewModelScope.launch {
            imageUseCase.saveLocalImages()
        }
        super.onCleared()
    }

    fun load() {
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

    fun saveImages() {
        viewModelScope.launch {
            imageUseCase.saveLocalImages()
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