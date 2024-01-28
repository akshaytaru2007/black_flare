package com.movieapppoc.movielist.util.network

import com.movieapppoc.movielist.util.exceptions.ClientErrorException
import com.movieapppoc.movielist.util.exceptions.ServerErrorException
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response


class HttpExceptionInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original: Request = chain.request()
        val response: Response = chain.proceed(original)

        // Check for HTTP 4xx and 5xx status codes
        if (!response.isSuccessful) {
            if (response.code in 400..499) {
                throw ClientErrorException(errorCode = response.code)
            } else if (response.code in 500..599) {
                throw ServerErrorException(response.code)
            }
        }
        return response
    }
}