package com.mohamed.tasks.comics.data.source.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "comics")
data class LocalComics(
    @PrimaryKey
    @ColumnInfo(name = "comics_id")
    var id: Int? = 0,
    var images: List<LocalImage>? = listOf(),
    var textObjects: List<LocalTextObject>? = listOf(),
)

data class LocalImage(
    val extension: String? = null,
    val path: String? = null,
)


data class LocalTextObject(
    val language: String? = null,
    val text: String? = null,
    val type: String? = null
)
