package com.mohamed.tasks.chefaa.di

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import com.mohamed.tasks.core.di.ImagesBaseUrl
import com.mohamed.tasks.chefaa.BuildConfig
import com.mohamed.tasks.chefaa.network.ApiKeyInterceptor
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.mohamed.tasks.comics.data.source.local.ComicsDao
import com.mohamed.tasks.comics.data.source.local.ComicsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor, apiKeyInterceptor: ApiKeyInterceptor
    ): OkHttpClient = OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor)
        .addInterceptor(apiKeyInterceptor).build()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder().baseUrl(BuildConfig.API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory()).client(okHttpClient).build()


    @Singleton
    @Provides
    fun provideRoomDataBase(@ApplicationContext context: Context): ComicsDatabase =
        Room.databaseBuilder(
            context,
            ComicsDatabase::class.java,
            "comics_database"
        )
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideRoomDao(db: ComicsDatabase): ComicsDao = db.dao

    @Provides
    @Singleton
    fun provideUserGson() = Gson()

    @Provides
    @Singleton
    @ImagesBaseUrl
    fun provideImagesBaseUrl() = "https://image.tmdb.org/t/p/original/"

}