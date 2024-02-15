package com.mohamed.tasks.details.domain.repository

import com.mohamed.tasks.details.domain.entity.ComicDetailsEntity

interface ComicDetailsRepository {
    suspend fun getComicDetails(id: Int): ComicDetailsEntity?
}