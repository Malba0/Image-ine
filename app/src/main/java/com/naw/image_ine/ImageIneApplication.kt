package com.naw.image_ine

import android.app.Application
import com.naw.image_ine.di.AppComponent
import com.naw.image_ine.di.DaggerAppComponent
import com.naw.image_ine.di.ImageModule

class ImageIneApplication : Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent
            .builder()
            .imageModule(ImageModule(this))
            .build()
    }
}