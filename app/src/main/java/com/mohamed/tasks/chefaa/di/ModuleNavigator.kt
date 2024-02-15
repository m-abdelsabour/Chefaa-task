package com.mohamed.tasks.chefaa.di

import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.mohamed.tasks.chefaa.AppNavigationDirections
import com.mohamed.tasks.chefaa.R
import com.mohamed.tasks.chefaa.navigation.ServiceNavigationImpl
import com.mohamed.tasks.chefaa.navigation.toComicsDetails
import com.mohamed.tasks.comics.domain.model.Comics
import com.mohamed.tasks.comics.presentation.navigation.ComicsNavigation
import com.mohamed.tasks.details.presentation.service.ServiceNavigation
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ServiceComponent
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

// if we want to nav oth object
@ActivityScoped
class ModuleNavigator @Inject constructor(
    private val navController: NavController,
) : ComicsNavigation {
    override fun navigateToDetails(comics: Comics) {
        val comicsDetails = comics.toComicsDetails()
        navController.navigate(AppNavigationDirections.actionDetails(comicsDetails))
    }

    @Module
    @InstallIn(ActivityComponent::class)
    object NavControllerModule {
        @Provides
        fun navController(activity: FragmentActivity): NavController {
            return NavHostFragment.findNavController(
                activity.supportFragmentManager.findFragmentById(
                    R.id.fragment_container_view
                )!!
            )
        }
    }

    @Module
    @InstallIn(ActivityComponent::class)
    abstract class ComicModule {
        @Binds
        abstract fun comic(moduleNavigator: ModuleNavigator): ComicsNavigation
    }



}

/*
@Module
@InstallIn(FragmentComponent::class)
abstract class NavigationModule {
    @Binds
    abstract fun bindMoviesNavigation(impl: ComicsNavigationImpl): ComicsNavigation
}*/
