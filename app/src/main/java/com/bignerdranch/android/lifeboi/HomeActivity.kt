package com.bignerdranch.android.lifeboi

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

const val EXTRA_USER_FOUND = "com.bignerdranch.android.lifeboi.user_found"
private const val REQUEST_EVENT_SCREEN = 10

class HomeActivity : AppCompatActivity(), HomeFragment.Callbacks {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val currentFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container)

        if (currentFragment == null) {
            val fragment = HomeFragment()
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
            .commit()
    }

    override fun onEventSelected() {
        val intent = AppointmentActivity.newIntent(this@HomeActivity)
        startActivityForResult(intent, REQUEST_EVENT_SCREEN)
    }

    companion object{
        fun newIntent(packageContext: Context) : Intent {
            return Intent(packageContext, HomeActivity::class.java).apply {
                putExtra(EXTRA_USER_FOUND, true)
            }
        }
    }
}