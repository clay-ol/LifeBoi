package com.bignerdranch.android.lifeboi

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

private const val APPOINTMENT_DATE = "com.bignerdranch.android.lifeboi.appointment_date"

class AppointmentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_appointment)

        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_appointment_container)
        if (currentFragment == null) {
            val fragment = AppointmentFragment()
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_appointment_container, fragment)
                .commit()
        }
    }

    companion object{
        fun newIntent(packageContext: Context) : Intent {
            return Intent(packageContext, HomeActivity::class.java).apply {
                putExtra(APPOINTMENT_DATE, true)
            }
        }
    }
}