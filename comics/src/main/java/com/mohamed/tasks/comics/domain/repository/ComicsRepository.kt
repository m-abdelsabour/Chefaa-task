package com.mohamed.tasks.comics.domain.repository

import com.mohamed.tasks.comics.domain.model.Comics

interface ComicsRepository {
    suspend fun loadComics()
    suspend fun getComics():List<Comics>
}