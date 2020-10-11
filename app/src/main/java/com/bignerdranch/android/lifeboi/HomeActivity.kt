package com.bignerdranch.android.lifeboi

import android.content.Context
import android.content.Intent
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat

const val EXTRA_USER_FOUND = "com.bignerdranch.android.lifeboi.user_found"
private const val REQUEST_EVENT_SCREEN = 10

class HomeActivity : AppCompatActivity(), HomeFragment.Callbacks {
    private var username = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        username = intent.getStringExtra(EXTRA_USER_FOUND).toString()
        Log.d("HomeActivity", "Got Username: ${username}")


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

    override fun onWeatherSelected() {
        val fragment = WeatherFragment()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment )
            .addToBackStack( null )
            .commit()
    }

    override fun onStepsSelected() {
        val fragment = StepsFragment()
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

    companion object{
        fun newIntent(packageContext: Context?, username: String) : Intent {
            return Intent(packageContext, HomeActivity::class.java).apply {
                putExtra(EXTRA_USER_FOUND, username)
            }
        }
    }
}