package com.bignerdranch.android.lifeboi

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button


private const val TAG = "HomeFragment"
private const val ARG_USERNAME = "username"

class HomeFragment : Fragment() {

    interface Callbacks {
        fun onWeatherSelected()
        fun onStepsSelected()
        fun onEventSelected()
    }

    private var callbacks: Callbacks? = null

    private lateinit var weatherButton: Button
    private lateinit var eventButton: Button
    private lateinit var stepsButton: Button

    private var username = ""


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
        username = arguments?.getSerializable(ARG_USERNAME) as String

        Log.d(TAG, "Got username: $username")
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
            callbacks?.onStepsSelected()

        }
        return view
    }

    companion object {
        fun newInstance(username: String): HomeFragment {
            val args = Bundle().apply {
                putSerializable(ARG_USERNAME, username)
            }

            return HomeFragment().apply{
                arguments = args
            }
        }
    }
}