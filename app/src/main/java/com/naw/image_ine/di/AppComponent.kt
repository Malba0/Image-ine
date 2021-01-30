package com.naw.image_ine.di

import android.content.Context
import com.naw.image_ine.ui.ImageViewModel
import dagger.Component

@Component(modules = [AppModule::class, ImageModule::class])
interface AppComponent {

    fun inject(imageViewModel: ImageViewModel)
    fun inject(context: Context)
}