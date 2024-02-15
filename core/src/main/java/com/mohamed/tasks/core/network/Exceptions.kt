package com.mohamed.tasks.core.network

object NoInternetException : Throwable()

data class ServerError(val errorMessage: String) : Throwable()