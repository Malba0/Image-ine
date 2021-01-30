package com.naw.image_ine.di

import android.content.Context
import com.naw.image_ine.ui.ImagesViewModel
import dagger.Component

@Component(modules = [AppModule::class, ImageModule::class])
interface AppComponent {

    fun inject(imagesViewModel: ImagesViewModel)
    fun inject(context: Context)
}