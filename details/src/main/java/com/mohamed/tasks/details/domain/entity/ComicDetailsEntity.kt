package com.mohamed.tasks.details.domain.entity

data class ComicDetailsEntity(
    val id: Int? = 0,
    val images: List<ComicImage>? = listOf(),
    val textObjects: List<ComicTextObject>? = listOf(),
)

data class ComicImage(
    val extension: String? = null,
    val path: String? = null,
)

data class ComicTextObject(
    val language: String? = null,
    val text: String? = null,
    val type: String? = null
)