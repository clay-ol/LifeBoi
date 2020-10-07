package com.bignerdranch.android.lifeboi.datamodel

import java.util.*

data class Appointment(var uuid: UUID = UUID.randomUUID(),
                       var location: String = "",
                       var Name: String = "",
                       var invitations: List<String> = emptyList(),
                       var startDate: Date = Date(),
                       var endDate: Date = Date()
)