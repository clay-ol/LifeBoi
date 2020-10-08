package com.bignerdranch.android.lifeboi

import android.app.Application
import com.bignerdranch.android.lifeboi.database.FirebaseClient

class LifeBoiApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        FirebaseClient.initialize(this)
    }
}