package com.bignerdranch.android.lifeboi

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bignerdranch.android.lifeboi.database.FirebaseClient
import com.bignerdranch.android.lifeboi.viewModels.AppointmentConfigureViewModel
import com.bignerdranch.android.lifeboi.viewModels.FitnessViewModel


private const val TAG = "HomeFragment"
private const val ARG_USERNAME = "username"

class HomeFragment : Fragment() {

    interface Callbacks {
        fun onWeatherSelected()
        fun onFitnessSelected()
        fun onEventSelected()
    }

    private var callbacks: Callbacks? = null

    private lateinit var appointmentNameTextView: TextView
    private lateinit var appointmentDateTextView: TextView
    private lateinit var weatherButton: Button
    private lateinit var eventButton: Button
    private lateinit var fitnessButton: Button

    private var username = ""
    private lateinit var firebaseClient: FirebaseClient


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
        firebaseClient = FirebaseClient.get()

        Log.d(TAG, "Got username: $username")

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

//        setContentView(R.layout.activity_home)

        appointmentNameTextView = view.findViewById(R.id.home_appointment_name) as TextView
        appointmentDateTextView = view.findViewById(R.id.home_appointment_date) as TextView
        weatherButton = view.findViewById(R.id.weather_button) as Button
        eventButton = view.findViewById(R.id.event_button) as Button
        fitnessButton = view.findViewById(R.id.fitness_button) as Button

        firebaseClient.getAppointment(username) {result ->

            if (result.id != "empty") {
                val appointmentName = "Appointment: ${result.name}"
                val appointmentDate = "Starts on ${result.startDate} and Ends on ${result.endDate}"

                appointmentNameTextView.text = appointmentName
                appointmentDateTextView.text = appointmentDate
            }

        }

        weatherButton.setOnClickListener {
            callbacks?.onWeatherSelected()

        }

        eventButton.setOnClickListener {
            callbacks?.onEventSelected()
        }

        fitnessButton.setOnClickListener {
            callbacks?.onFitnessSelected()

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