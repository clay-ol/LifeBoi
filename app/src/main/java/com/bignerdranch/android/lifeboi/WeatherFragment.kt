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
import kotlin.math.round


private const val TAG = "WeatherFragment"

class WeatherFragment : Fragment() {

    private var latitude: Double = 42.2626
    private var longitude: Double = -71.8023
    private lateinit var feelsLikeText: TextView
    private lateinit var latText: TextView
    private lateinit var longText: TextView
    private lateinit var dayMinTempText: TextView
    private lateinit var dayMaxTempText: TextView
    private lateinit var currentTempText: TextView
    private lateinit var weatherDescText: TextView
    private lateinit var sunriseText: TextView
    private lateinit var sunsetText: TextView
    private lateinit var dayHumidText: TextView
    private lateinit var dayWindText: TextView
    private lateinit var dayVisText: TextView
    private lateinit var dayCloudyText: TextView
    

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        latitude = arguments?.getSerializable("lat") as Double
        longitude = arguments?.getSerializable( "lon" ) as Double
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_weather, container, false)


        dayMinTempText = view.findViewById( R.id.dayLow ) as TextView
        dayMaxTempText = view.findViewById( R.id.dayHigh ) as TextView
        currentTempText = view.findViewById(R.id.currentTemp) as TextView
        weatherDescText = view.findViewById( R.id.currentCon ) as TextView

        dayHumidText = view.findViewById(R.id.humidity) as TextView
        dayWindText = view.findViewById( R.id.wind ) as TextView
        dayVisText = view.findViewById( R.id.visibility ) as TextView
        dayCloudyText = view.findViewById( R.id.clouds ) as TextView

        feelsLikeText = view.findViewById( R.id.feelslike ) as TextView

        sunriseText = view.findViewById( R.id.sunrise ) as TextView
        sunsetText = view.findViewById( R.id.sunset ) as TextView

        latText = view.findViewById(R.id.lat) as TextView
        longText = view.findViewById(R.id.lon ) as TextView

        val currentCurrentWeatherData: LiveData<WeatherResponse> = WeatherFetcher( latitude.toString(), longitude.toString() ).fetchWeather()
        currentCurrentWeatherData.observe(
            viewLifecycleOwner,
            Observer { weatherItems ->
                Log.d(TAG, "Response received: $weatherItems")

                latText.text = "Latitude: ${latitude}"
                longText.text = "Longitude: ${longitude}"


                // windspeed and visibility are not always available for all locations, so we check if they are returned or not
                var windspeed = weatherItems.daily[0].windspeed
                if( windspeed == null ){
                    windspeed = "unavailable"
                }

                var visibility = weatherItems.daily[0].visibility
                if( visibility == null ){
                    visibility = "unavailable"
                }

                currentTempText.text =
                    "Current Temp (C): ${ toCelsius( weatherItems.current.temp.toDouble() ) }"
                weatherDescText.text = weatherItems.current.weather[0].main

                dayMaxTempText.text = "Daily High: ${ toCelsius(weatherItems.daily[0].temp.max.toDouble() ) }"
                dayMinTempText.text = "Daily Low: ${ toCelsius( weatherItems.daily[0].temp.min.toDouble() ) }"

                feelsLikeText.text = "Feels Like: ${ toCelsius( weatherItems.daily[0].feels_like.day.toDouble() ) }"

                sunriseText.text = "Sunrise Time: ${ weatherItems.daily[0].sunrise }"
                sunsetText.text = "Sunset Time: ${ weatherItems.daily[0].sunset }"
                dayHumidText.text = "Daily Humidity: ${weatherItems.daily[0].humidity}"
                dayVisText.text = "Daily Visibility (m): ${ visibility }"
                dayCloudyText.text = "Daily Cloud Level: ${weatherItems.daily[0].clouds}"
                dayWindText.text = "Daily Windspeed: ${ windspeed }"
            })
        return view
    }

    override fun onStart() {
        super.onStart()
    }

    private fun toCelsius( temp: Double ): Double {
        return round( temp - 273.15 )
    }
    companion object {
        fun newInstance(lat: Double, lon: Double): WeatherFragment {
            val args = Bundle().apply {
                putSerializable("lat", lat)
                putSerializable("lon", lon)
            }

            return WeatherFragment().apply{
                arguments = args
            }
        }
    }
}