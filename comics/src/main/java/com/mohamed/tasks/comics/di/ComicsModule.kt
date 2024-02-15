package com.mohamed.tasks.comics.di

import com.mohamed.tasks.comics.data.repository.ComicsRepositoryImpl
import com.mohamed.tasks.comics.data.source.remote.ComicsApi
import com.mohamed.tasks.comics.domain.repository.ComicsRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit
import retrofit2.create

@Module
@InstallIn(ViewModelComponent::class)
abstract class ComicsModule {
    @Binds
    abstract fun bindComicsRepository(imp: ComicsRepositoryImpl): ComicsRepository

    companion object {
        @Provides
        fun provideComicsApi(retrofit: Retrofit): ComicsApi = retrofit.create()
    }
}