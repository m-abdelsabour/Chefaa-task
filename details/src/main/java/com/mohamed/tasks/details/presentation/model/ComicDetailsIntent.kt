package com.mohamed.tasks.details.presentation.model

sealed class ComicDetailsIntent {
    data class GetDetails(val id: Int) : ComicDetailsIntent()
}