package com.bignerdranch.android.lifeboi

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CalendarView
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment

class ConfigureAppointmentsFragment : Fragment() {

    private lateinit var startDateEditText: EditText
    private lateinit var endDateEditText: EditText
    private lateinit var nameEditText: EditText
    private lateinit var locationEditText: EditText
    private lateinit var invitationEditText: EditText
    private lateinit var submitButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_calendar, container, false)

        startDateEditText = view.findViewById(R.id.appointment_start) as EditText
        endDateEditText = view.findViewById(R.id.appointment_end) as EditText
        nameEditText = view.findViewById(R.id.appointment_name) as EditText
        locationEditText = view.findViewById(R.id.appointment_location) as EditText
        invitationEditText = view.findViewById(R.id.appointment_invite) as EditText
        submitButton = view.findViewById(R.id.appointment_submit) as Button

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        startDateEditText.apply {
            isEnabled = false
            setOnClickListener {
                Log.d(DEBUG, "Start Date has been touched")
            }
        }

        endDateEditText.apply {
            isEnabled = false
            setOnClickListener {
                // TODO:
            }
        }

        locationEditText.setOnClickListener {

        }

        submitButton.setOnClickListener {
            if(startDateEditText.text.isEmpty() || endDateEditText.text.isEmpty()
                || nameEditText.text.isEmpty()) {
                Toast.makeText(context, "Complete Fields...", Toast.LENGTH_SHORT).show()
            } else {
            }

            Log.d(DEBUG, "Submit button worked")

        }

    }

    companion object {
        fun newInstance() : ConfigureAppointmentsFragment {
            return ConfigureAppointmentsFragment()
        }
    }
}