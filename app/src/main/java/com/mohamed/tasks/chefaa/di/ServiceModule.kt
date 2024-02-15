package com.mohamed.tasks.chefaa.di

import com.mohamed.tasks.chefaa.navigation.ServiceNavigationImpl
import com.mohamed.tasks.details.presentation.service.ServiceNavigation
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ServiceComponent

@Module
@InstallIn(ServiceComponent::class)
object ServiceModule {
    @Provides
    fun provideUploadManager(): ServiceNavigation {
        return ServiceNavigationImpl()
    }
}