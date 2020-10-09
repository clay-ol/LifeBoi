package com.bignerdranch.android.lifeboi

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer


private const val TAG = "WeatherFragment"

class WeatherFragment : Fragment() {

    private lateinit var latText: TextView
    private lateinit var longText: TextView
    private lateinit var currentWeatherText: TextView
    private lateinit var locationManager: LocationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_weather, container, false)
        currentWeatherText = view.findViewById(R.id.currentWeather) as TextView
        latText = view.findViewById(R.id.latitude) as TextView
        longText = view.findViewById(R.id.latitude) as TextView

//        locationManager?.let{ this.locationManager =
//            context.getSystemService( Context.LOCATION_SERVICE )
//        }

        val currentWeatherData: LiveData<WeatherItem> = WeatherFetcher().fetchWeather()
        currentWeatherData.observe(
            viewLifecycleOwner,
            Observer { weatherItems ->
                Log.d(TAG, "Response received: $weatherItems")
                currentWeatherText.text =
                    "City: Worcester Current Temp (C): ${(weatherItems.temp).toDouble() - 273.15}"
            })
        return view
    }

    override fun onStart() {
        super.onStart()

    }
}