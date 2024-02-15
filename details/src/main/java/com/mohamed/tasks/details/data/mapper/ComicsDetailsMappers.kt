package com.mohamed.tasks.details.data.mapper

import com.mohamed.tasks.details.data.source.remote.ComicsModel
import com.mohamed.tasks.details.domain.entity.ComicDetailsEntity
import com.mohamed.tasks.details.domain.entity.ComicImage
import com.mohamed.tasks.details.domain.entity.ComicTextObject


fun ComicsModel.toComicsEntity() = ComicDetailsEntity(
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