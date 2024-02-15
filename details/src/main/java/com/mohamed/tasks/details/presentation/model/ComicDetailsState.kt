package com.mohamed.tasks.details.presentation.model

import com.mohamed.tasks.details.domain.entity.ComicDetailsEntity

data class ComicDetailsState(
    val isLoading: Boolean = false,
    val comicDetails: ComicDetailsEntity? = null,
    val errorMsg: String? = null,
    val errorRes: Int? = null
)