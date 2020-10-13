package com.bignerdranch.android.lifeboi

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Service
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.view.get
import androidx.core.view.size
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
private const val CONTACT = 1
private const val GPS_LOCATION = 2
private const val END_DATE = "end_date"

class ConfigureAppointmentsFragment : Fragment() {

    interface Callbacks {
        fun onDatePickSelected(electedDate: Int)
    }

    private lateinit var startDateEditText: EditText
    private lateinit var endDateEditText: EditText
    private lateinit var nameEditText: EditText
    private lateinit var locationEditText: EditText
    private lateinit var invitationButton: Button
    private lateinit var submitButton: Button
    private lateinit var inviteeChipGroup: ChipGroup
    private lateinit var appointmentConfigureViewModel: AppointmentConfigureViewModel

    private lateinit var chosenDate: LocalDate

    private var callbacks: Callbacks? = null
    private var dateType: Int = 3

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(DEBUG, "ConfigureAppointments onCreate()")

        chosenDate = arguments?.getSerializable(CHOSEN_DATE) as LocalDate
        dateType = arguments?.getSerializable(ELECTED_DATE) as Int
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

        Log.d(DEBUG, "Received $chosenDate")
    }


    @SuppressLint("MissingPermission")
    override fun onStart() {
        super.onStart()

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

        locationEditText.apply {
            // TODO: for now leave it as it is...
            isFocusable = false
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
            setOnClickListener {
                val msg = TextMessenger.newInstance()
                msg.send("7603358848", "HI TED")
//                startActivityForResult(locationIntent, GPS_LOCATION)
//                Log.d(DEBUG, "${location?.longitude}")
            }
        }

        submitButton.setOnClickListener {
            if(startDateEditText.text.isEmpty() || endDateEditText.text.isEmpty()
                || nameEditText.text.isEmpty()) {
                Toast.makeText(context, "Complete Fields...", Toast.LENGTH_SHORT).show()
            } else {
//                val appointment = Appointment()
//                FirebaseClient.get().addAppointment()
            }

            Log.d(DEBUG, "Submit button worked")

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

//            val packageManager: PackageManager = requireActivity().packageManager
//            val resolvedAct: ResolveInfo? = packageManager.resolveActivity(pickContactIntent, PackageManager.MATCH_DEFAULT_ONLY)
//
//            if(resolvedAct == null) {
//                isEnabled = false
//            }
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
                        if (number == "") number = "This contact is not saved into your device"
                        else {

                            var doesExist = false

                            for(i in 0 until inviteeChipGroup.childCount) {
                                val chipName : Chip = inviteeChipGroup.getChildAt(i) as Chip
                                if(chipName.text == invitee) {
                                    doesExist = true
                                    break;
                                }
                            }

                            if(!doesExist) {
                                val inviteeChip = Chip(requireContext())
                                inviteeChip.text = invitee
                                inviteeChipGroup.addView(inviteeChip)
                            }

                        }
                        // TODO: update invitation field
                        Log.d(DEBUG, "$invitee, $number")

                    }
            }
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

    companion object {

        @RequiresApi(Build.VERSION_CODES.O)
        fun newInstance(date: LocalDate, electedDate: Int) : ConfigureAppointmentsFragment {
            val args = Bundle().apply {
                putSerializable(CHOSEN_DATE, date)
                putSerializable(ELECTED_DATE, electedDate)
            }

            return ConfigureAppointmentsFragment().apply {
                arguments = args
            }
        }
    }
}