package com.movieapppoc.movielist.util.network

import android.os.Handler
import android.util.Log
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import kotlin.math.pow

class HttpRetryInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        // try the request
        val response: Array<Response> = arrayOf<Response>(chain.proceed(request))
        var tryCount = 0
        while (!response[0].isSuccessful && tryCount < NUMBER_OF_RETRIES) {
            Log.d(TAG, "retrying with count $tryCount")
            val expDelay =
                (RETRY_DELAY * 2.0.pow(Math.max(0, NUMBER_OF_RETRIES - 1).toDouble())).toInt()
            tryCount++
            Handler().postDelayed({
                try {
                    response[0] = chain.call().clone().execute()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }, expDelay.toLong())
        }
        // otherwise just pass the original response on
        return response[0]
    }

    companion object {
        private const val TAG = "CustomRetry: " + "RetryInterceptor"
        private const val NUMBER_OF_RETRIES = 4
        private const val RETRY_DELAY = 300.0
    }
}