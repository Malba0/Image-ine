package com.naw.image_ine.di

import android.app.Application
import android.content.Context
import com.naw.image_ine.domain.ImageUseCase
import com.naw.image_ine.services.LocalImageRepository
import com.naw.image_ine.services.OnlineImageRepository
import dagger.Module
import dagger.Provides

@Module
class ImageModule() {

//    @Provides
//    fun providesLocalImageRepository(application: Application): LocalImageRepository {
//        return LocalImageRepository(application.baseContext)
//    }
//
//    @Provides
//    fun providesOnlineImageRepository(): OnlineImageRepository {
//        return OnlineImageRepository()
//    }
//
//    @Provides
//    fun providesImageUseCase(
//        onlineImageRepository: OnlineImageRepository,
//        localImageRepository: LocalImageRepository
//    ): ImageUseCase {
//        return ImageUseCase(onlineImageRepository, localImageRepository)
//    }
}