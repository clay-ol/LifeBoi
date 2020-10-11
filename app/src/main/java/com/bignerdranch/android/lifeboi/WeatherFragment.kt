package com.bignerdranch.android.lifeboi

import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.bignerdranch.android.lifeboi.weatherapi.WeatherResponse
import kotlinx.android.synthetic.main.fragment_weather.*
import org.w3c.dom.Text


private const val TAG = "WeatherFragment"
private const val WEATHER_ARG_USERNAME = "username"

class WeatherFragment : Fragment() {

    private var latitude: Double = 42.2626
    private var longitude: Double = -71.8023
    private lateinit var locationText: TextView
    private lateinit var latText: TextView
    private lateinit var longText: TextView
    private lateinit var dayMinTempText: TextView
    private lateinit var dayMaxTempText: TextView
    private lateinit var currentTempText: TextView
    private lateinit var weatherDescText: TextView

    private lateinit var dayHumidText: TextView
    private lateinit var dayWindText: TextView
    private lateinit var dayVisText: TextView
    private lateinit var dayCloudyText: TextView


    private var username = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        username = arguments?.getSerializable(WEATHER_ARG_USERNAME) as String

        Log.d(TAG, "Got username: $username")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_weather, container, false)

        locationText = view.findViewById( R.id.location ) as TextView
        dayMinTempText = view.findViewById( R.id.dayLow ) as TextView
        dayMaxTempText = view.findViewById( R.id.dayHigh ) as TextView
        currentTempText = view.findViewById(R.id.currentTemp) as TextView
        weatherDescText = view.findViewById( R.id.currentCon ) as TextView

        dayHumidText = view.findViewById(R.id.humidity) as TextView
        dayWindText = view.findViewById( R.id.wind ) as TextView
        dayVisText = view.findViewById( R.id.visibility ) as TextView
        dayCloudyText = view.findViewById( R.id.clouds ) as TextView

        latText = view.findViewById(R.id.lat) as TextView
        longText = view.findViewById(R.id.lon ) as TextView

        val currentCurrentWeatherData: LiveData<WeatherResponse> = WeatherFetcher().fetchWeather()
        currentCurrentWeatherData.observe(
            viewLifecycleOwner,
            Observer { weatherItems ->

                latitude = weatherItems.lat.toDouble()
                longitude = weatherItems.lon.toDouble()
                Log.d(TAG, "Response received: $weatherItems")

                locationText.text = "Worcester"
                latText.text = "Latitude: ${latitude}"
                longText.text = "Longitude: ${longitude}"

                currentTempText.text =
                    "Current Temp (C): ${(weatherItems.current.temp).toDouble() - 273.15}"
                weatherDescText.text = weatherItems.current.weather[0].main

                dayMaxTempText.text = "Daily High: ${weatherItems.daily[0].temp.max.toDouble() - 273.15 }"
                dayMinTempText.text = "Daily Low: ${weatherItems.daily[0].temp.min.toDouble() - 273.15 }"

                dayHumidText.text = "Daily Humidity: ${weatherItems.daily[0].humidity}"
                dayVisText.text = "Daily Visibility (m): ${weatherItems.daily[0].visibility}"
                dayCloudyText.text = "Daily Cloud Level: ${weatherItems.daily[0].clouds}"
                dayWindText.text = "Daily Windspeed: ${weatherItems.daily[0].windspeed}"
            })
        return view
    }

    override fun onStart() {
        super.onStart()
    }

    companion object {
        fun newInstance(username: String): WeatherFragment {
            val args = Bundle().apply {
                putSerializable(WEATHER_ARG_USERNAME, username)
            }

            return WeatherFragment().apply{
                arguments = args
            }
        }
    }
}