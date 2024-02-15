package com.mohamed.tasks.details.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ComicsDetails(
    val id: Int? = 0,
    val images: List<ComicDetailsImage>? = listOf(),
    val textObjects: List<ComicDetailsTextObject>? = listOf(),
):Parcelable

@Parcelize
data class ComicDetailsImage(
    val extension: String? = null,
    val path: String? = null,
):Parcelable

@Parcelize
data class ComicDetailsTextObject(
    val language: String? = null,
    val text: String? = null,
    val type: String? = null
) : Parcelable
