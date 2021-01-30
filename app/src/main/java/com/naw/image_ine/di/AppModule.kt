package com.naw.image_ine.di

import android.app.Application
import dagger.Module
import dagger.Provides

@Module
class AppModule(private var application: Application) {

    @Provides
    internal fun providesApplication(): Application {
        return application
    }
}