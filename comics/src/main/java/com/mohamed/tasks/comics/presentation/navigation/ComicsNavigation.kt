package com.mohamed.tasks.comics.presentation.navigation

import com.mohamed.tasks.comics.domain.model.Comics

interface ComicsNavigation {
    // if we want to nav with deeplink
    //fun getDetailsDeepLink(id: Int?): String
    //if we want to nav with comic object
    fun navigateToDetails(comics: Comics)
}