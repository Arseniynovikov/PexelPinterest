package com.example.pinterest.repository

import com.example.pinterest.data.database.BookmarkDao
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
    fun providePhotoRepository(api: PexelApi, dao: BookmarkDao): PhotoRepository =
        PhotoRepositoryImpl(api, dao)
}