package com.bignerdranch.android.lifeboi

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import java.time.LocalDate

private const val APPOINTMENT_DATE = "com.bignerdranch.android.lifeboi.appointment_date"

class AppointmentActivity : AppCompatActivity(), CalendarFragment.Callbacks, AppointmentListFragment.Callbacks, ConfigureAppointmentsFragment.Callbacks {

    private var dateType: Int = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_appointment)

        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_appointment_container)
        if (currentFragment == null) {
            val fragment = AppointmentListFragment.newInstance()
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_appointment_container, fragment)
                .commit()
        }
    }

    override fun onDatePickSelected(electedDate: Int) {
        Log.d(DEBUG, "onEditSelected() called")
        dateType = electedDate
        val fragment = CalendarFragment.newInstance()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_appointment_container, fragment)
            .addToBackStack( null)
            .commit()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onAddSelected() {
        val fragment = ConfigureAppointmentsFragment.newInstance(LocalDate.now(), dateType)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_appointment_container, fragment)
            .addToBackStack( null)
            .commit()
    }

    override fun onEditSelected() {
//        Log.d(DEBUG, "onEditSelected() called")
//        val fragment = CalendarFragment.newInstance()
//        supportFragmentManager
//            .beginTransaction()
//            .replace(R.id.fragment_appointment_container, fragment)
//            .addToBackStack( null)
//            .commit()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onDateSelected(date: LocalDate) {
        val fragment = ConfigureAppointmentsFragment.newInstance(date, dateType)

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