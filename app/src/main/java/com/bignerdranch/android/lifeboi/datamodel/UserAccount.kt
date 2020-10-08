package com.bignerdranch.android.lifeboi.datamodel

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class UserAccount(var email: String? = "",
                       var first_name: String? = "",
                       var last_name: String? = "",
                       var password: String? = "",
                       var phone_number: String? = "")
