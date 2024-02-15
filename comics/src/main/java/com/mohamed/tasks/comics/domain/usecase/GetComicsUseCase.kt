package com.mohamed.tasks.comics.domain.usecase

import com.mohamed.tasks.comics.domain.model.Comics
import com.mohamed.tasks.comics.domain.model.exception.EmptyComicsException
import com.mohamed.tasks.comics.domain.repository.ComicsRepository
import javax.inject.Inject

class GetComicsUseCase @Inject constructor(private val repository: ComicsRepository) {
    suspend operator fun invoke(): List<Comics> {
        repository.loadComics()
        return  repository.getComics()
    }
}