package com.bignerdranch.android.lifeboi

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProviders
import com.bignerdranch.android.lifeboi.viewModels.FitnessViewModel

const val EXTRA_USER_FOUND = "com.bignerdranch.android.lifeboi.user_found"
private const val REQUEST_EVENT_SCREEN = 10
private const val REQUEST_SPLASH = 0

private const val TAG = "HomeActivity"

class HomeActivity : AppCompatActivity(), HomeFragment.Callbacks {
    private var username = ""

    private var latitude = 42.2626
    private var longitude = -71.8023

    val fitnessViewModel: FitnessViewModel by lazy {
        ViewModelProviders.of(this).get(FitnessViewModel::class.java)
    }

    private lateinit var locationManager: LocationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        username = intent.getStringExtra(EXTRA_USER_FOUND).toString()
        Log.d("HomeActivity", "Got Username: ${username}")

        locationManager = getSystemService( Context.LOCATION_SERVICE ) as LocationManager
        getLocation()

        val currentFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container)

        if (currentFragment == null) {
            val fragment = HomeFragment.newInstance(username)
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit()
        }

    }

    override  fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun goToSplash() {
        val intent = SplashActivity.newIntent(this@HomeActivity)
        startActivityForResult(intent, REQUEST_SPLASH)
    }

    override fun onWeatherSelected() {
        getLocation()
        val fragment = WeatherFragment.newInstance( latitude, longitude )
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment )
            .addToBackStack( null )
            .commit()
    }

    override fun onFitnessSelected() {
//        var stepsBundle = Bundle()
//        stepsBundle.putInt( "steps", steps )
        val fragment = FitnessFragment()
//        fragment.arguments?.putInt("steps", steps )
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment )
            .addToBackStack( null )
            .commit()
    }

    override fun onEventSelected() {
        Log.d("HomeActivity", "Passing Username: $username to AppointmentActivity")
        val intent = AppointmentActivity.newIntent(this@HomeActivity, username)
        startActivityForResult(intent, REQUEST_EVENT_SCREEN)
    }

    private fun getLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Log.d( TAG, "No permissions :(")
            return
        }else {

            var locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            if (locationGPS != null) {
                latitude = locationGPS.latitude
                longitude = locationGPS.longitude
                Log.d( TAG, "Location received: Latitude - ${latitude}, Longitude - ${longitude}" )
            } else {
                latitude = 42.2626
                longitude = -71.8023
            }
        }
    }

    companion object{
        fun newIntent(packageContext: Context?, username: String) : Intent {
            return Intent(packageContext, HomeActivity::class.java).apply {
                putExtra(EXTRA_USER_FOUND, username)
            }
        }
    }
}