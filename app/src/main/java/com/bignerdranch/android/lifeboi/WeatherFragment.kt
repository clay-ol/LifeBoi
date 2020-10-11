package com.bignerdranch.android.lifeboi

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

class WeatherFragment : Fragment() {

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


//        locationManager?.let{ this.locationManager =
//            context?.getSystemService( Context.LOCATION_SERVICE ) as LocationManager
//        }
//        if (this.context?.let {
//                ActivityCompat.checkSelfPermission(
//                    it,
//                    Manifest.permission.ACCESS_FINE_LOCATION
//                )
//            } != PackageManager.PERMISSION_GRANTED && this.context?.let {
//                ActivityCompat.checkSelfPermission(
//                    it,
//                    Manifest.permission.ACCESS_COARSE_LOCATION
//                )
//            } != PackageManager.PERMISSION_GRANTED
//        ) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return view
//        }
//        var locationListener = object : LocationListener{
//            override fun onLocationChanged(p0: Location) {
//                var latitude = p0!!.latitude
//                var longitude = p0!!.longitude
//
//                latText.text = latitude.toString()
//                longText.text = longitude.toString()
//
//            }
//        }

//        locationManager = getSystemService( Context.LOCATION_SERVICE )
        val currentCurrentWeatherData: LiveData<WeatherResponse> = WeatherFetcher().fetchWeather()
        currentCurrentWeatherData.observe(
            viewLifecycleOwner,
            Observer { weatherItems ->
                Log.d(TAG, "Response received: $weatherItems")

                locationText.text = "Worcester"
                latText.text = "Latitude: ${weatherItems.lat}"
                longText.text = "Longitude: ${weatherItems.lon}"

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
}