package com.example.pinterest

import com.example.pinterest.pexelApi.PexelApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun providePhotoRepository(api: PexelApi): PhotoRepository =
        PhotoRepositoryImpl(api)
}