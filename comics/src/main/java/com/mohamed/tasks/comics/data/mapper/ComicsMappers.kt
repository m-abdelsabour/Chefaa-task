package com.mohamed.tasks.comics.data.mapper

import com.mohamed.tasks.comics.data.source.local.LocalComics
import com.mohamed.tasks.comics.data.source.local.LocalImage
import com.mohamed.tasks.comics.data.source.local.LocalTextObject
import com.mohamed.tasks.comics.data.source.remote.ComicsModel
import com.mohamed.tasks.comics.domain.model.ComicImage
import com.mohamed.tasks.comics.domain.model.ComicTextObject
import com.mohamed.tasks.comics.domain.model.Comics

fun ComicsModel.toComicsEntity() = LocalComics(
    images = images?.map {
        LocalImage(extension = it.extension, path = it.path)
    } ?: emptyList(),
    textObjects = textObjects?.map {
        LocalTextObject(
            language = it.language,
            text = it.text,
            type = it.type
        )
    } ?: emptyList(),
    id = id ?: 0,
)

fun LocalComics.toComics() = Comics(
    images = images?.map {
        ComicImage(extension = it.extension, path = it.path)
    } ?: emptyList(),
    textObjects = textObjects?.map {
        ComicTextObject(
            language = it.language,
            text = it.text,
            type = it.type
        )
    } ?: emptyList(),
    id = id ?: 0,
)

fun ComicsModel.toComics() = Comics(
    images = images?.map {
        ComicImage(extension = it.extension, path = it.path)
    }
        ?: emptyList(),
    textObjects = textObjects?.map {
        ComicTextObject(
            language = it.language,
            text = it.text,
            type = it.type
        )
    } ?: emptyList(),
    id = id ?: 0,
)