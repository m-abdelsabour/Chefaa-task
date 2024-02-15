package com.mohamed.tasks.comics.data.source.remote

import retrofit2.http.GET

interface ComicsApi {
    @GET("public/comics")
    suspend fun getComics(): ComicsResponseModel
}