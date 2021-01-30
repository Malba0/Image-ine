package com.naw.image_ine.ui

import android.app.Application
import androidx.lifecycle.*
import com.naw.image_ine.domain.ImageUseCase
import com.naw.image_ine.services.LocalImageRepository
import com.naw.image_ine.services.OnlineImageRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class ImagesViewModel(application: Application) : AndroidViewModel(application) {

//    @Inject
//    lateinit var imageUseCase: ImageUseCase
    private val imageUseCase = ImageUseCase(
        OnlineImageRepository(),
        LocalImageRepository(application.applicationContext)
    )

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