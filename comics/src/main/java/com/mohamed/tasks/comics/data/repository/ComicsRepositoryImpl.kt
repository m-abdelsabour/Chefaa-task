package com.mohamed.tasks.comics.data.repository

import com.mohamed.tasks.comics.data.mapper.toComics
import com.mohamed.tasks.comics.data.mapper.toComicsEntity
import com.mohamed.tasks.comics.data.source.local.ComicsDao
import com.mohamed.tasks.comics.data.source.remote.ComicsApi
import com.mohamed.tasks.comics.domain.model.Comics
import com.mohamed.tasks.comics.domain.repository.ComicsRepository
import com.mohamed.tasks.core.di.IODispatcher
import com.mohamed.tasks.core.network.mapNetworkErrors
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class ComicsRepositoryImpl @Inject constructor(
    private val api: ComicsApi,
    private val dao: ComicsDao,
    @IODispatcher private val dispatcher: CoroutineDispatcher,
) : ComicsRepository {

    /* override suspend fun loadComics() {
         return withContext(dispatcher) {
             try {
                 api.getComics().data?.results?.map { it.toComics()}
                     ?: throw EmptyComicsException()
             } catch (t: Throwable) {
                 Timber.e(t)
                 throw t.mapNetworkErrors()
             }
         }
     }*/
    override suspend fun loadComics() = withContext(dispatcher) {
        try {
            updateLocalDataBase()
        } catch (t: Throwable) {
            Timber.e(t)
            if (dao.getAllComics().isEmpty())
                throw t.mapNetworkErrors()
        }
    }


    override suspend fun getComics(): List<Comics> = withContext(dispatcher) {
        dao.getAllComics().map { it.toComics() }
    }

    private suspend fun updateLocalDataBase() {
        val comics = api.getComics()
        dao.insertAll(comics.data?.results?.map { it.toComicsEntity() })
    }

}