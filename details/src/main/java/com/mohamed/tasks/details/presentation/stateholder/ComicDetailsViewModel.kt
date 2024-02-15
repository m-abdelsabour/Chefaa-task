package com.mohamed.tasks.details.presentation.stateholder

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohamed.tasks.core.R
import com.mohamed.tasks.core.di.MainDispatcher
import com.mohamed.tasks.core.network.NoInternetException
import com.mohamed.tasks.core.network.ServerError
import com.mohamed.tasks.details.domain.GetComicDetailsUseCase
import com.mohamed.tasks.details.presentation.model.ComicDetailsIntent
import com.mohamed.tasks.details.presentation.model.ComicDetailsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ComicDetailsViewModel @Inject constructor(
    private val getComicDetailsUseCase: GetComicDetailsUseCase,
    @MainDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {
    private val _state = MutableStateFlow(ComicDetailsState())
    val state: StateFlow<ComicDetailsState> = _state

    private val _intent: MutableSharedFlow<ComicDetailsIntent> = MutableSharedFlow()
    private val intent = _intent.asSharedFlow()


    init {
        viewModelScope.launch {
            intent.collect {
                processIntent(it)
            }
        }
    }

    fun sendIntent(intent: ComicDetailsIntent) {
        viewModelScope.launch {
            _intent.emit(intent)
        }
    }

    private fun processIntent(intent: ComicDetailsIntent) {
        when (intent) {
            is ComicDetailsIntent.GetDetails -> loadMovieDetails(intent.id)
        }
    }

    private fun loadMovieDetails(id: Int) {
        viewModelScope.launch(dispatcher) {
            try {
                _state.value = _state.value.copy(isLoading = true, errorRes = null, errorMsg = null)
                val comic = getComicDetailsUseCase(id)
                _state.value =
                    _state.value.copy(isLoading = false, comicDetails = comic)
            } catch (t: Throwable) {
                mapError(t)
            }
        }
    }

    private fun mapError(t: Throwable) {
        viewModelScope.launch {
            _state.value = when (t) {
                is NoInternetException -> _state.value.copy(
                    isLoading = false, errorRes = R.string.internet_error
                )

                is ServerError -> _state.value.copy(isLoading = false, errorMsg = t.errorMessage)
                else -> _state.value.copy(isLoading = false, errorRes = R.string.general_error)
            }
        }
    }
}