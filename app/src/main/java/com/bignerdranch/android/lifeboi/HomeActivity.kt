package com.bignerdranch.android.lifeboi

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlin.math.roundToInt

const val EXTRA_USER_FOUND = "com.bignerdranch.android.lifeboi.user_found"
private const val REQUEST_EVENT_SCREEN = 10
private const val TAG = "HomeActivity"

class HomeActivity : AppCompatActivity(), HomeFragment.Callbacks, SensorEventListener {
    private var username = ""

    private var latitude = 42.2626
    private var longitude = -71.8023
    private var running = false
    private var steps = 0
    private lateinit var locationManager: LocationManager
    private var sensorManager: SensorManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        username = intent.getStringExtra(EXTRA_USER_FOUND).toString()

        sensorManager = getSystemService( Context.SENSOR_SERVICE ) as SensorManager
//        locationManager = getSystemService( Context.LOCATION_SERVICE ) as LocationManager
//        locationManager.requestLocationUpdates( LocationManager.GPS_PROVIDER, minTime)


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
        running = true
        var stepsSensor = sensorManager?.getDefaultSensor( Sensor.TYPE_STEP_COUNTER )

        if ( stepsSensor == null ){
            Log.d( TAG, "No step sensor" )
        }
        else {
            sensorManager?.registerListener( this, stepsSensor, SensorManager.SENSOR_DELAY_UI )
        }
    }

    override fun onPause() {
        super.onPause()
        running = false
        sensorManager?.unregisterListener( this )
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
    }

    override fun onSensorChanged( event: SensorEvent ) {
        if( running ) {
            steps = event.values[0].roundToInt()
        }
    }

    override fun onWeatherSelected() {
        getLocation()
        val fragment = WeatherFragment()
        fragment?.arguments?.putDouble("lat", latitude )
        fragment?.arguments?.putDouble("lon", longitude )
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment )
            .addToBackStack( null )
            .commit()
    }

    override fun onStepsSelected() {
//        var stepsBundle = Bundle()
//        stepsBundle.putInt( "steps", steps )
        val fragment = StepsFragment()
        fragment.arguments?.putInt("steps", steps )
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment )
            .addToBackStack( null )
            .commit()
    }

    override fun onEventSelected() {
        val intent = AppointmentActivity.newIntent(this@HomeActivity)
        startActivityForResult(intent, REQUEST_EVENT_SCREEN)
    }

    fun getLocation() {
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