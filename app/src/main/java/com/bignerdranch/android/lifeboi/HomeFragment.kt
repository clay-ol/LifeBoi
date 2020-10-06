package com.bignerdranch.android.lifeboi

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button


private const val TAG = "HomeFragment"

class HomeFragment : Fragment() {

    interface Callbacks {
        fun onWeatherSelected()
        fun onEventSelected()
    }

    private var callbacks: Callbacks? = null

    private lateinit var weatherButton: Button
    private lateinit var eventButton: Button
    private lateinit var stepsButton: Button


    override fun onAttach( context: Context){
        super.onAttach(context)
        callbacks = context as Callbacks?
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

//        setContentView(R.layout.activity_home)

        weatherButton = view.findViewById(R.id.weather_button) as Button
        eventButton = view.findViewById(R.id.event_button) as Button
        stepsButton = view.findViewById(R.id.steps_button) as Button

        weatherButton.setOnClickListener {
            callbacks?.onWeatherSelected()

        }

        eventButton.setOnClickListener {
            callbacks?.onEventSelected()
        }

        stepsButton.setOnClickListener {

        }
        return view
    }
}