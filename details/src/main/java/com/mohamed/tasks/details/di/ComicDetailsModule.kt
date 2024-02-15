package com.mohamed.tasks.details.di

import com.mohamed.tasks.details.data.repository.ComicDetailsRepositoryImpl
import com.mohamed.tasks.details.data.source.remote.ComicDetailsApi
import com.mohamed.tasks.details.domain.repository.ComicDetailsRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit
import retrofit2.create

@Module
@InstallIn(ViewModelComponent::class)
abstract class ComicDetailsModule {

    @Binds
    abstract fun bindComicDetailsRepository(imp: ComicDetailsRepositoryImpl): ComicDetailsRepository

    companion object {
        @Provides
        fun provideComicDetailsApi(retrofit: Retrofit): ComicDetailsApi = retrofit.create()
    }
}