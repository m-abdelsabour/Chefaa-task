package com.mohamed.tasks.comics.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Comics(
    val id: Int? = 0,
    val images: List<ComicImage>? = listOf(),
    val textObjects: List<ComicTextObject>? = listOf(),
):Parcelable

@Parcelize
data class ComicImage(
    val extension: String? = null,
    val path: String? = null,
):Parcelable

@Parcelize
data class ComicTextObject(
    val language: String? = null,
    val text: String? = null,
    val type: String? = null
) : Parcelable
