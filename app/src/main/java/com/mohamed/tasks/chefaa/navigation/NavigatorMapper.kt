package com.mohamed.tasks.chefaa.navigation

import com.mohamed.tasks.comics.domain.model.Comics
import com.mohamed.tasks.details.presentation.model.ComicDetailsImage
import com.mohamed.tasks.details.presentation.model.ComicDetailsTextObject
import com.mohamed.tasks.details.presentation.model.ComicsDetails

fun Comics.toComicsDetails() = ComicsDetails(
    images = images?.map {
        ComicDetailsImage(extension = it.extension, path = it.path)
    } ?: emptyList(),
    textObjects = textObjects?.map {
        ComicDetailsTextObject(
            language = it.language,
            text = it.text,
            type = it.type
        )
    } ?: emptyList(),
    id = id ?: 0,
)