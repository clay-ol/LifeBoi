package com.bignerdranch.android.lifeboi.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel

private const val TAG = "FitnessViewModel"

class FitnessViewModel : ViewModel () {

    var pushupState: Boolean = false
    var pullupState: Boolean = false
    var squatState: Boolean = false
    var legliftState: Boolean = false

    init {
        Log.d( TAG, "ViewModel instance created" )
    }

    override fun onCleared() {
        super.onCleared()
        Log.d( TAG, "ViewModel instance about to be destroyed" )
    }
}