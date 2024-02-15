package com.mohamed.tasks.details.data.source.remote

import com.google.gson.annotations.SerializedName

data class RemoteComicsDetails(
    @SerializedName("code")
    val code: Int? = 0, // 200
    @SerializedName("data")
    val `data`: Data? = Data(),
    @SerializedName("status")
    val status: String? = "" // Ok
)

data class Data(
    @SerializedName("count")
    val count: Int? = 0, // 20
    @SerializedName("limit")
    val limit: Int? = 0, // 20
    @SerializedName("offset")
    val offset: Int? = 0, // 2
    @SerializedName("results")
    val results: List<ComicsModel>? = listOf(),
    @SerializedName("total")
    val total: Int? = 0 // 59076
)

data class ComicsModel(
    @SerializedName("id")
    val id: Int? = 0, // 82970
    @SerializedName("images")
    val images: List<Image>? = listOf(),
    @SerializedName("textObjects")
    val textObjects: List<TextObject>? = listOf(),
)

data class Image(
    @SerializedName("extension")
    val extension: String? = null, // jpg
    @SerializedName("path")
    val path: String? = null, // http://i.annihil.us/u/prod/marvel/i/mg/c/80/5e3d7536c8ada
)

data class TextObject(
    @SerializedName("language")
    val language: String? = null, // en-us
    @SerializedName("text")
    val text: String? = null, // "Re-live the legendary first journey into the dystopian future of 2013 - where Sentinels stalk the Earth, and the X-Men are humanity's only hope...until they die! Also featuring the first appearance of Alpha Flight, the return of the Wendigo, the history of the X-Men from Cyclops himself...and a demon for Christmas!? "
    @SerializedName("type")
    val type: String? = null // issue_solicit_text
)


