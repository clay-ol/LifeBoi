package com.bignerdranch.android.lifeboi.datamodel

import java.util.*

class Appointment(var id: String = UUID.randomUUID().toString(),
                       var location: String = "",
                       var host: String = "",
                       var name: String = "",
                       var invitations: List<String> = emptyList(),
                       var startDate: String = "",
                       var endDate: String = "",
                       var isInvitee: Boolean = false,
                       var phoneNumber: String = "")