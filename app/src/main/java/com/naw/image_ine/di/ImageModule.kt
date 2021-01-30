package com.naw.image_ine.di

import android.content.Context
import com.naw.image_ine.services.LocalImageRepository
import com.naw.image_ine.services.OnlineImageRepository
import dagger.Module
import dagger.Provides

@Module
class ImageModule(private var context: Context) {

    @Provides
    fun providesLocalImageRepository(): LocalImageRepository {
        return LocalImageRepository(context)
    }

    @Provides
    fun providesOnlineImageRepository(): OnlineImageRepository {
        return OnlineImageRepository()
    }
}