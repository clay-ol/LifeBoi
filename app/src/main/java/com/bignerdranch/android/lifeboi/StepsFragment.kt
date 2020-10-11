package com.bignerdranch.android.lifeboi

import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.LOCATION_SERVICE

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.getSystemService


private const val TAG = "StepsFragment"

class StepsFragment : Fragment() {

    private lateinit var stepsView: TextView
    private var steps: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if ( arguments != null ){
            steps = arguments!!.getInt("steps" )
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_steps, container, false)
        stepsView = view.findViewById( R.id.steps ) as TextView
        stepsView.text = "Steps: ${steps}. Wow!"
        return view
    }
    override fun onStart() {
        super.onStart()

    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

}