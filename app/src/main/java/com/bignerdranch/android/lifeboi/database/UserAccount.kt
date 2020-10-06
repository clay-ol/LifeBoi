package com.bignerdranch.android.lifeboi.database

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class UserAccount(var first_name: String? = "",
                       var last_name: String? = "",
                       var phone_number: Int? = 0)
