package com.bignerdranch.android.lifeboi

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.bignerdranch.android.lifeboi.database.FirebaseClient
import com.bignerdranch.android.lifeboi.datamodel.Appointment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import java.io.Serializable
import java.time.LocalDate

private const val GET_APPOINTMENT = "get_appointment"

class ViewAppointmentFragment: Fragment() {

    interface Callbacks {
        fun onBackSelected()
    }

    private lateinit var eventNameTextView: TextView
    private lateinit var startDateTextView: TextView
    private lateinit var endDateTextView: TextView
    private lateinit var hostTextView: TextView
    private lateinit var locationTextView: TextView
    private lateinit var backButton: Button
    private lateinit var deleteButton: Button
    private lateinit var appointment: Appointment
    private lateinit var invitationChipGroup: ChipGroup

    private var callbacks: Callbacks? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        appointment = arguments?.getSerializable(GET_APPOINTMENT) as Appointment

    }

    override fun onStart() {
        super.onStart()

        backButton.apply {
            setOnClickListener {
                callbacks?.onBackSelected()
            }
        }

        deleteButton.apply {
            setOnClickListener {
                Log.d(DEBUG, "DELETING APPOINTMENT FOR ${appointment.host}")
                FirebaseClient.get().deleteAppointment(appointment.id, appointment.phoneNumber, appointment.invitee)
                callbacks?.onBackSelected()
            }
        }
    }

    override fun onAttach(context: Context){
        super.onAttach(context)
        callbacks = context as Callbacks
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
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
        invitationChipGroup = view.findViewById(R.id.view_invitees) as ChipGroup

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        eventNameTextView.text = appointment.name
        startDateTextView.text = appointment.startDate
        endDateTextView.text = appointment.endDate
        hostTextView.text = appointment.host
        if(appointment.location != "") {
            locationTextView.text = appointment.location
        } else {
            locationTextView.text = "Host did not provide a location"
        }

        for(i in appointment.invitations.indices) {
            val invitee = appointment.invitations[i]

            if(invitee == appointment.host) {
                continue
            }

            val inviteeChip = Chip(requireContext())
            inviteeChip.text = invitee
            invitationChipGroup.addView(inviteeChip)

            Log.d(DEBUG, "PEOPLE: ${appointment.invitations[i]}")
        }

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