package com.mohamed.tasks.details.data.repository

import com.mohamed.tasks.core.di.IODispatcher
import com.mohamed.tasks.core.network.mapNetworkErrors
import com.mohamed.tasks.details.data.mapper.toComicsEntity
import com.mohamed.tasks.details.data.source.remote.ComicDetailsApi
import com.mohamed.tasks.details.domain.entity.ComicDetailsEntity
import com.mohamed.tasks.details.domain.repository.ComicDetailsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class ComicDetailsRepositoryImpl @Inject constructor(
    private val api: ComicDetailsApi,
    @IODispatcher private val dispatcher: CoroutineDispatcher,
) : ComicDetailsRepository {
    override suspend fun getComicDetails(id: Int): ComicDetailsEntity? {
        return withContext(dispatcher) {
            try {
                api.getComicDetails(id).data?.results?.firstOrNull()?.toComicsEntity()
            } catch (t: Throwable) {
                Timber.e(t)
                throw t.mapNetworkErrors()
            }
        }
    }
}