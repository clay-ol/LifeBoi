package com.bignerdranch.android.lifeboi

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.bignerdranch.android.lifeboi.datamodel.Appointment
import java.io.Serializable
import java.time.LocalDate

private const val GET_APPOINTMENT = "get_appointment"

class ViewAppointmentFragment: Fragment() {

    interface Callbacks {
        fun onBackSelected()
    }

    private lateinit var eventNameTextView: View
    private lateinit var startDateTextView: View
    private lateinit var endDateTextView: View
    private lateinit var hostTextView: View
    private lateinit var locationTextView: View
    private lateinit var backButton: Button
    private lateinit var deleteButton: Button
    private lateinit var appointment: Appointment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        appointment = arguments?.getSerializable(GET_APPOINTMENT) as Appointment

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onStart() {
        super.onStart()

        backButton.apply {
            setOnClickListener {

            }
        }

        deleteButton.apply {
            setOnClickListener {

            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_view_appointment, container, false)
        eventNameTextView = view.findViewById(R.id.view_name) as TextView
        startDateTextView = view.findViewById(R.id.view_start) as TextView
        endDateTextView = view.findViewById(R.id.view_end) as TextView
        hostTextView = view.findViewById(R.id.view_host) as TextView
        locationTextView = view.findViewById(R.id.view_location) as TextView
        backButton = view.findViewById(R.id.view_back_button) as Button
        deleteButton = view.findViewById(R.id.view_delete_button) as Button

        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onDetach() {
        super.onDetach()
    }

    companion object {

        fun newInstance(appointment: Appointment): ViewAppointmentFragment {
            val args = Bundle().apply {
                putSerializable(GET_APPOINTMENT, appointment)
            }

            return ViewAppointmentFragment().apply {
                arguments = args
            }
        }

    }

}