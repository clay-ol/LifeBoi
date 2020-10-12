package com.bignerdranch.android.lifeboi.datamodel

import com.google.firebase.firestore.GeoPoint
import java.util.*

class Appointment(var id: String = UUID.randomUUID().toString(),
                       var location: GeoPoint = GeoPoint(1.2, 1.2),
                       var host: String = "",
                       var name: String = "",
                       var invitations: List<String> = emptyList(),
                       var startDate: String = "",
                       var endDate: String = "",
                       var isInvitee: Boolean = false,
                       var phoneNumber: String = "")