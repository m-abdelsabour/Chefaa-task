package com.mohamed.tasks.details.data.source.remote

import retrofit2.http.GET
import retrofit2.http.Path

interface ComicDetailsApi {
    @GET("public/comics/{id}")
    suspend fun getComicDetails(@Path("id") id: Int):RemoteComicsDetails
}