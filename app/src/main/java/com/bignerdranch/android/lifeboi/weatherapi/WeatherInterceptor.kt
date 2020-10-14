package com.bignerdranch.android.lifeboi.weatherapi

import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class WeatherInterceptor(latitude: String, longitude: String ) : Interceptor {

    val latitude = latitude
    val longitude = longitude
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest: Request = chain.request()

        val newUrl: HttpUrl = originalRequest.url().newBuilder()
            .addQueryParameter("lat", latitude )
            .addQueryParameter("lon", longitude)
            .addQueryParameter("exclude", "minutely")
            .addQueryParameter("appid", "bd0d6f55a110a7763eda5a7c0ada5e1e" )
            .build()

        val newRequest: Request = originalRequest.newBuilder()
            .url(newUrl)
            .build()

        return chain.proceed(newRequest)
    }
}