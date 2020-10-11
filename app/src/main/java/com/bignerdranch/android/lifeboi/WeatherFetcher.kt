package com.bignerdranch.android.lifeboi

import retrofit2.Call
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bignerdranch.android.lifeboi.weatherapi.OpenWeatherApi
import com.bignerdranch.android.lifeboi.weatherapi.WeatherInterceptor
import com.bignerdranch.android.lifeboi.weatherapi.WeatherResponse
import okhttp3.OkHttpClient
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val TAG = "WeatherFetcher"

class WeatherFetcher( latitude: String, longitude: String ) {

    private val openWeatherApi: OpenWeatherApi

    init {
        val client = OkHttpClient.Builder()
            .addInterceptor(WeatherInterceptor( latitude, longitude ))
            .build()
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org")
            .addConverterFactory(GsonConverterFactory.create())
            .client( client )
            .build()
        openWeatherApi = retrofit.create(OpenWeatherApi::class.java)
    }


    fun fetchWeather(): LiveData<WeatherResponse> {
        val responseLiveData: MutableLiveData<WeatherResponse> = MutableLiveData()
        val weatherResponse: Call<WeatherResponse> = openWeatherApi.fetchWeather()


        weatherResponse.enqueue(object : Callback<WeatherResponse> {

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                Log.e(TAG, "Failed to fetch weather", t)
            }

            override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                Log.d(TAG, "Response received" + response )
                val weatherResponse: WeatherResponse? = response.body()
                responseLiveData.value = ( weatherResponse )
            }
        })
        return responseLiveData
    }
}