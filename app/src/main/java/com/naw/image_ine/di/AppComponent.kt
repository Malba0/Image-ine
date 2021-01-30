package com.naw.image_ine.di

import android.content.Context
import com.naw.image_ine.MainActivity
import com.naw.image_ine.ui.ImagesViewModel
import dagger.Component

@Component(modules = [AppModule::class, ImageModule::class])
interface AppComponent {

    fun inject(imagesViewModel: ImagesViewModel)
    fun inject(mainActivity: MainActivity)

//    fun context(): Context
}