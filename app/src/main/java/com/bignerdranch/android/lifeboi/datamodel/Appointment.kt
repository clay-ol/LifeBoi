package com.bignerdranch.android.lifeboi.datamodel

import java.util.*

data class Appointment(var id: String = UUID.randomUUID().toString(),
                       var location: String = "",
                       var host: String = "",
                       var name: String = "",
                       var invitations: List<String> = emptyList(),
                       var startDate: Date = Date(),
                       var endDate: Date = Date()
)