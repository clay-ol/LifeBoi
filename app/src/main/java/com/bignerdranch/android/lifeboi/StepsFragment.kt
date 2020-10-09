package com.bignerdranch.android.lifeboi

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bignerdranch.android.lifeboi.R
import androidx.core.content.ContextCompat.getSystemService

private const val TAG = "StepsFragment"

class StepsFragment : Fragment(), SensorEventListener {

    private lateinit var stepsView: TextView
    var isRunning = false
    var sensorManager:SensorManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_steps, container, false)
//        sensorManager = getSystemService( fuck this ) as SensorManager
        stepsView = view.findViewById( R.id.steps ) as TextView
        return view
    }
    override fun onStart() {
        super.onStart()

    }

    override fun onResume() {
        super.onResume()
        isRunning = true
        var stepSensor = sensorManager?.getDefaultSensor( Sensor.TYPE_STEP_COUNTER )
        if( stepSensor == null ){
            Log.d( TAG, "No sensor available")
        }
        else {
            sensorManager?.registerListener( this, stepSensor, SensorManager.SENSOR_DELAY_UI )
        }
    }

    override fun onPause() {
        super.onPause()
        isRunning = false
        sensorManager?.unregisterListener(this)
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
        TODO("Not yet implemented")
    }

    override fun onSensorChanged(event: SensorEvent) {
        if( isRunning ) {
            stepsView.setText( "" + event.values[0] )
        }
    }
}