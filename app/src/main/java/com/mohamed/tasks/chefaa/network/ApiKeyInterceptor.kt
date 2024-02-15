package com.mohamed.tasks.chefaa.network

import com.mohamed.tasks.chefaa.BuildConfig
import com.mohamed.tasks.core.util.UtilHelper
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject


class ApiKeyInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalHttpUrl: HttpUrl = chain.request().url

        val timeStamp=System.currentTimeMillis().toString()
        val url =
            originalHttpUrl.newBuilder()
                .addQueryParameter("apikey", BuildConfig.API_KEY)
                .addQueryParameter("ts", timeStamp)
                .addQueryParameter(
                    "hash",
                    UtilHelper.md5("${timeStamp}${BuildConfig.PRIVATE_KEY}${BuildConfig.API_KEY}")
                )
                .build()
        val requestBuilder: Request.Builder = chain.request().newBuilder().url(url)

        val request: Request = requestBuilder.build()
        return chain.proceed(request)
    }

}