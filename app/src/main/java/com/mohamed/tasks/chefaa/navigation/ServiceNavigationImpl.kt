package com.mohamed.tasks.chefaa.navigation

import android.content.Context
import com.mohamed.tasks.chefaa.MainActivity
import com.mohamed.tasks.comics.presentation.navigation.ComicsNavigation
import com.mohamed.tasks.details.R
import com.mohamed.tasks.details.presentation.service.ServiceNavigation
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject


class ServiceNavigationImpl @Inject constructor() :
    ServiceNavigation {
    override fun getHomeClassName(): Class<*> {
        return MainActivity::class.java
    }


}
