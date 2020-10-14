package com.bignerdranch.android.lifeboi

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.bignerdranch.android.lifeboi.database.FirebaseClient
import com.bignerdranch.android.lifeboi.datamodel.Appointment
import com.bignerdranch.android.lifeboi.twilioapi.TextMessenger
import com.bignerdranch.android.lifeboi.viewModels.AppointmentConfigureViewModel
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import java.time.LocalDate


private const val CHOSEN_DATE = "date_of_choice"
private const val ELECTED_DATE = "is_start_date"
private const val USERNAME = "username_configure"
private const val CONTACT = 1

class ConfigureAppointmentsFragment : Fragment() {

    interface Callbacks {
        fun onDatePickSelected(electedDate: Int)
        fun onSubmitSelected()
        fun onBackAppointmentSelected()
    }

    private lateinit var startDateEditText: EditText
    private lateinit var endDateEditText: EditText
    private lateinit var nameEditText: EditText
    private lateinit var locationEditText: EditText
    private lateinit var invitationButton: Button
    private lateinit var submitButton: Button
    private lateinit var backButton: Button
    private lateinit var inviteeChipGroup: ChipGroup
    private lateinit var appointmentConfigureViewModel: AppointmentConfigureViewModel

    private lateinit var chosenDate: LocalDate
    private lateinit var username: String
    private var callbacks: Callbacks? = null
    private var dateType: Int = 3

    private val textWatcher = object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {
            appointmentConfigureViewModel.location = locationEditText.text.toString()
            appointmentConfigureViewModel.nameOfEvent = nameEditText.text.toString()
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(DEBUG, "ConfigureAppointments onCreate()")

        chosenDate = arguments?.getSerializable(CHOSEN_DATE) as LocalDate
        dateType = arguments?.getSerializable(ELECTED_DATE) as Int
        username = arguments?.getSerializable(USERNAME) as String

        appointmentConfigureViewModel = activity?.let { ViewModelProviders.of(it).get(
            AppointmentConfigureViewModel::class.java
        ) }!!

        when (dateType) {
            1 -> appointmentConfigureViewModel.startDate = chosenDate.toString()
            2 -> appointmentConfigureViewModel.endDate = chosenDate.toString()
            else -> {
                appointmentConfigureViewModel.startDate = chosenDate.toString()
                appointmentConfigureViewModel.endDate = chosenDate.toString()
            }
        }
    }


    @SuppressLint("MissingPermission")
    override fun onStart() {
        super.onStart()

        nameEditText.apply {
            addTextChangedListener(textWatcher)
        }

        startDateEditText.apply {
            isFocusable = false
            setOnClickListener {
                callbacks?.onDatePickSelected(1)
            }
        }

        endDateEditText.apply {
            isFocusable = false
            setOnClickListener {
                callbacks?.onDatePickSelected(2)
            }
        }

        backButton.apply {
            setOnClickListener {
                callbacks?.onBackAppointmentSelected()
            }
        }

        locationEditText.apply {
//            val locationManager = activity?.getSystemService(LOCATION_SERVICE) as LocationManager
//            val location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
//            var gmmLocation = ""
//
//            gmmLocation = if(location?.latitude != null && location.longitude != null) {
//                "geo:" + location.latitude + "," + location.longitude
//            } else {
//                "geo:42.66999,-72.321"
//            }
//
//            val gmmLocationUri = Uri.parse(gmmLocation)
//            val locationIntent = Intent(Intent.ACTION_VIEW, gmmLocationUri).setPackage("com.google.android.apps.maps")

            addTextChangedListener(textWatcher)
            setOnClickListener {

            }


        }

        submitButton.setOnClickListener {
            if(nameEditText.text.isEmpty()) {
                Toast.makeText(context, "Please name the event", Toast.LENGTH_SHORT).show()
            } else {

                commitAppointment()
                clearAppointment()
                callbacks?.onSubmitSelected()
                Toast.makeText(context, "Appointment made...", Toast.LENGTH_LONG).show()

            }
        }

        invitationButton.apply {
            isFocusable = false
            val pickContactIntent = Intent(
                Intent.ACTION_PICK,
                ContactsContract.Contacts.CONTENT_URI
            )

            setOnClickListener {
                startActivityForResult(pickContactIntent, CONTACT)
            }

        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_configure_appointment, container, false)

        startDateEditText = view.findViewById(R.id.appointment_start) as EditText
        endDateEditText = view.findViewById(R.id.appointment_end) as EditText
        nameEditText = view.findViewById(R.id.appointment_name) as EditText
        locationEditText = view.findViewById(R.id.appointment_location) as EditText
        invitationButton = view.findViewById(R.id.appointment_invite) as Button
        submitButton = view.findViewById(R.id.appointment_submit) as Button
        inviteeChipGroup = view.findViewById(R.id.appointment_chip) as ChipGroup
        backButton = view.findViewById(R.id.appointment_back) as Button

        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when {
            resultCode != Activity.RESULT_OK -> return

            requestCode == CONTACT && data != null -> {

                    val contactUri: Uri? = data.data
                    val queryFields = arrayOf(ContactsContract.Contacts.DISPLAY_NAME)
                    val cursor = contactUri?.let {
                        requireActivity().contentResolver
                            .query(it, queryFields, null, null, null)
                    }
                    cursor?.use {
                        if (it.count == 0) {
                            return
                        }

                        it.moveToFirst()
                        val invitee = it.getString(0)

                        var number = ""

                        val selection = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " like'%" + invitee + "%'"
                        val projection = arrayOf(ContactsContract.CommonDataKinds.Phone.NUMBER)
                        val c = context!!.contentResolver.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            projection, selection, null, null
                        )
                        if (c?.moveToFirst()!!) {
                            if (c != null) {
                                number = c.getString(0)
                            }
                        }
                        c?.close()

                        if(number != "") {
                            addInvitation(invitee, number)
                        }
                        Log.d(DEBUG, "$invitee, $number")

                    }
            }
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        for((key, value) in appointmentConfigureViewModel.invitationList) {
            addInvitation(value, key)
        }

        locationEditText.setText(appointmentConfigureViewModel.location)
        nameEditText.setText(appointmentConfigureViewModel.nameOfEvent)
        startDateEditText.setText(appointmentConfigureViewModel.startDate)
        endDateEditText.setText(appointmentConfigureViewModel.endDate)

    }

    override fun onAttach(context: Context){
        super.onAttach(context)
        callbacks = context as Callbacks
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    private fun clearAppointment() {
        appointmentConfigureViewModel.resetAppointment()
        endDateEditText.setText(LocalDate.now().toString())
        startDateEditText.setText(LocalDate.now().toString())
        nameEditText.setText("")
        locationEditText.setText("")
        inviteeChipGroup.removeAllViews()
    }

    private fun addInvitation(invitee: String, number: String) {
        var doesExist = false

        val numberStripped = number
            .replace("(","")
            .replace(")", "")
            .replace("-", "")
            .replace(" ", "")

        for(i in 0 until inviteeChipGroup.childCount) {
            val chipName : Chip = inviteeChipGroup.getChildAt(i) as Chip
            if(chipName.text == invitee) {
                doesExist = true
                break
            }
        }

        if(!doesExist) {
            val inviteeChip = Chip(requireContext())
            inviteeChip.text = invitee
            inviteeChip.isCloseIconVisible = true
            inviteeChip.setOnCloseIconClickListener {
                inviteeChipGroup.removeView(inviteeChip)
                appointmentConfigureViewModel.invitationList.remove(invitee)
            }
            inviteeChipGroup.addView(inviteeChip)
            appointmentConfigureViewModel.invitationList[numberStripped] = invitee
        }

    }

    private fun sendTexts(list: HashMap<String, String>, event: String, location: String, start: String, end: String) {

        for((key, value) in list) {
            val message = if(location != "") {
                "Hey, $value! $username has invited you to $event at $location starting at $start to $end"
            } else {
                "Hey, $value! $username has invited you to $event starting at $start to $end"
            }

            Log.d(DEBUG, "$message ")

            TextMessenger.newInstance().send(key, message)
        }

    }

    private fun commitAppointment() {
        val appointment = Appointment()
        appointment.startDate = startDateEditText.text.toString()
        appointment.endDate = endDateEditText.text.toString()
        appointment.name = nameEditText.text.toString()
        appointment.location = appointmentConfigureViewModel.location

        Log.d(DEBUG, "${appointment.location}, ${appointmentConfigureViewModel.location}, ${locationEditText.text}")

        // create appointment for host
        appointment.host = username
        appointment.invitee = false
        appointment.invitations = appointmentConfigureViewModel.invitationList.values.toList()
        appointment.invitations += listOf(username)
        sendTexts(
            appointmentConfigureViewModel.invitationList,
            appointment.name,
            appointment.location,
            appointment.startDate,
            appointment.endDate
        )

        Log.d(DEBUG, "HOST IS: ${appointment.host}")

        FirebaseClient.get().addAppointment(appointment)

        // create appointment for invitees
        appointment.invitee = true

        for ((key, value) in appointmentConfigureViewModel.invitationList) {
            appointment.phoneNumber = key
            FirebaseClient.get().getUsername(key) {
                if(it != "") {
                    appointment.host = it
                    FirebaseClient.get().addAppointment(appointment)
                    Log.d(DEBUG, "HOST IS: ${appointment.host}")

                }
            }
        }



    }

    companion object {

        @RequiresApi(Build.VERSION_CODES.O)
        fun newInstance(date: LocalDate, electedDate: Int, username: String) : ConfigureAppointmentsFragment {
            val args = Bundle().apply {
                putSerializable(CHOSEN_DATE, date)
                putSerializable(ELECTED_DATE, electedDate)
                putSerializable(USERNAME, username)
            }

            return ConfigureAppointmentsFragment().apply {
                arguments = args
            }
        }
    }
}