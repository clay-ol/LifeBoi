package com.bignerdranch.android.lifeboi

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

const val EXTRA_USER_FOUND = "com.bignerdranch.android.lifeboi.user_found"
class HomeActivity : AppCompatActivity() {

    private lateinit var weatherButton: Button
    private lateinit var eventButton: Button
    private lateinit var stepsButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        weatherButton = findViewById(R.id.weather_button)
        eventButton = findViewById(R.id.event_button)
        stepsButton = findViewById(R.id.steps_button)

        weatherButton.setOnClickListener {
            val intent = Intent(this, AppointmentActivity::class.java)
            startActivity(intent)
        }

        eventButton.setOnClickListener {

        }

        stepsButton.setOnClickListener {

        }
    }

    companion object{
        fun newIntent(packageContext: Context) : Intent {
            return Intent(packageContext, HomeActivity::class.java).apply {
                putExtra(EXTRA_USER_FOUND, true)
            }
        }
    }
}