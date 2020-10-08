package com.bignerdranch.android.lifeboi.viewModels

import androidx.lifecycle.ViewModel
import com.bignerdranch.android.lifeboi.datamodel.Appointment

class AppointmentListViewModel : ViewModel() {

    //TEMPORARY. FOR TESTING

    val appointments = mutableListOf<Appointment>()

    init {
        for(i in 0 until 4) {
            val appointment = Appointment()
            appointment.Name = "Appointment # ${i + 1}"
            appointments += appointment
        }
    }

}