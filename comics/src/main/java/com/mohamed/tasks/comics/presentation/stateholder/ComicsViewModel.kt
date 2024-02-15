package com.mohamed.tasks.comics.presentation.stateholder

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohamed.tasks.comics.domain.model.Comics
import com.mohamed.tasks.core.R
import com.mohamed.tasks.core.di.MainDispatcher
import com.mohamed.tasks.core.network.NoInternetException
import com.mohamed.tasks.core.network.ServerError
import com.mohamed.tasks.comics.domain.usecase.GetComicsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ComicsViewModel @Inject constructor(
    private val getComicsUseCase: GetComicsUseCase,
    @MainDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _comicsStateFlow: MutableStateFlow<List<Comics>> = MutableStateFlow(emptyList())
    val comicsStateFlow = _comicsStateFlow.asStateFlow()

    private val _loadingChannel: Channel<Boolean> = Channel()
    val loadingChannel = _loadingChannel.receiveAsFlow()

    private val _errorMsgChannel: Channel<String> = Channel()
    val errorMsgChannel = _errorMsgChannel.receiveAsFlow()

    private val _errorResChannel: Channel<Int> = Channel()
    val errorResChannel = _errorResChannel.receiveAsFlow()

    init {
        getComics()
    }

     fun getComics() {
        viewModelScope.launch(dispatcher) {
            setLoading(true)
            try {
                val comics = getComicsUseCase()
                _comicsStateFlow.value = comics
                setLoading(false)
            } catch (t: Throwable) {
                setLoading(false)
                mapError(t)
            }
        }
    }

    private fun setLoading(isLoading: Boolean) =
        viewModelScope.launch(dispatcher) { _loadingChannel.send(isLoading) }

    private fun mapError(t: Throwable) {
        viewModelScope.launch {
            when (t) {
                is NoInternetException -> _errorResChannel.send(R.string.internet_error)
                is ServerError -> _errorMsgChannel.send(t.errorMessage)
                else -> _errorResChannel.send(R.string.general_error)
            }
        }
    }
}