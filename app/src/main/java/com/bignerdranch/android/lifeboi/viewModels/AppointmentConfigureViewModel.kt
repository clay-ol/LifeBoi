package com.bignerdranch.android.lifeboi.viewModels

import androidx.lifecycle.ViewModel
import java.time.LocalDate

class AppointmentConfigureViewModel : ViewModel() {

    var startDate: String = LocalDate.now().toString()
    var endDate: String = LocalDate.now().toString()
    var nameOfEvent: String = ""
    var location: String = ""
    var invitationList = hashMapOf<String, String>()

    fun resetAppointment() {
        startDate = LocalDate.now().toString()
        endDate = LocalDate.now().toString()
        nameOfEvent = ""
        location = ""
        invitationList.clear()
    }
}