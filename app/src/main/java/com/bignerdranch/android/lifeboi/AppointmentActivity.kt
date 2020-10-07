package com.bignerdranch.android.lifeboi

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

private const val APPOINTMENT_DATE = "com.bignerdranch.android.lifeboi.appointment_date"

class AppointmentActivity : AppCompatActivity(), CalendarFragment.Callbacks {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_appointment)

        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_appointment_container)
        if (currentFragment == null) {
            val fragment = CalendarFragment.newInstance()
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_appointment_container, fragment)
                .commit()
        }
    }

    override fun onDateSelected() {
        Log.d(DEBUG, "onDateSelected()")
        val fragment = AppointmentListFragment.newInstance()
        Log.d(DEBUG, "onDateSelected() Fragment made")

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_appointment_container, fragment)
            .addToBackStack( null)
            .commit()
    }

    companion object{
        fun newIntent(packageContext: Context) : Intent {
            return Intent(packageContext, AppointmentActivity::class.java).apply {
                putExtra(APPOINTMENT_DATE, true)
            }
        }
    }
}