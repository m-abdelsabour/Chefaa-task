package com.mohamed.tasks.details.domain

import com.mohamed.tasks.details.domain.entity.ComicDetailsEntity
import com.mohamed.tasks.details.domain.repository.ComicDetailsRepository
import javax.inject.Inject

class GetComicDetailsUseCase @Inject constructor(private val repository: ComicDetailsRepository) {
    suspend operator fun invoke(id: Int): ComicDetailsEntity? {
        return repository.getComicDetails(id)
    }
}