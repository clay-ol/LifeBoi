package com.bignerdranch.android.lifeboi.datamodel

import java.util.*

data class Appointment(var uuid: UUID, var location: String, var Name: String, var invitations: List<String>, var startDate: Date, var endDate: Date)