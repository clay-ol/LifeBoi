package com.bignerdranch.android.lifeboi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

private const val TAG = "WeatherFragment"

class WeatherFragment : Fragment() {

    // NOTE - THERE CURRENTLY IS NO API KEY IN THE API
    private lateinit var currentWeatherText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_weather, container, false)
        currentWeatherText = view.findViewById( R.id.currentWeather ) as TextView
        return view
    }
    override fun onStart() {
        super.onStart()

    }
}