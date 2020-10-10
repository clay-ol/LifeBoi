package com.bignerdranch.android.lifeboi

import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
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
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.bignerdranch.android.lifeboi.viewModels.AppointmentConfigureViewModel
import java.time.LocalDate

private const val CHOSEN_DATE = "date_of_choice"
private const val ELECTED_DATE = "is_start_date"
private const val CONTACT = 5
//private const val END_DATE = "end_date"

class ConfigureAppointmentsFragment : Fragment() {

    interface Callbacks {
        fun onDatePickSelected(electedDate: Int)
    }

    private lateinit var startDateEditText: EditText
    private lateinit var endDateEditText: EditText
    private lateinit var nameEditText: EditText
    private lateinit var locationEditText: EditText
    private lateinit var invitationEditText: EditText
    private lateinit var submitButton: Button
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
        appointmentConfigureViewModel = activity?.let { ViewModelProviders.of(it).get(AppointmentConfigureViewModel::class.java) }!!


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
        invitationEditText = view.findViewById(R.id.appointment_invite) as EditText
        submitButton = view.findViewById(R.id.appointment_submit) as Button

        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d(DEBUG, "Received: $resultCode, $requestCode")

        when {
            resultCode != Activity.RESULT_OK -> return

            requestCode == CONTACT && data != null -> {
                val contactUri: Uri? = data.data

                val queryFields = arrayOf(ContactsContract.Contacts.DISPLAY_NAME)
                val queryFieldsID = arrayOf(ContactsContract.Contacts._ID)


                val cursor = contactUri?.let { requireActivity().contentResolver.query(it, queryFields, null, null, null) }
                cursor?.use {
                    if(it.count == 0) {
                        return
                    }

                    it.moveToFirst()
                    val invitee = it.getString(0)
                    Log.d(DEBUG, "Received: $invitee")

                }

                val cursorID = requireActivity().contentResolver.query(contactUri!!, queryFieldsID, null, null, null)
                cursorID?.use {
                    if(it.count == 0) {
                        return
                    }

                    it.moveToFirst()
                    val contactId = it.getString(0)

                    val phoneURI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
                    val phoneNumberQueryFields = arrayOf(ContactsContract.CommonDataKinds.Phone.NUMBER)

                    val phoneWhere = "${ContactsContract.CommonDataKinds.Phone.CONTACT_ID} = ?"

                    val phoneQueryParam = arrayOf(contactId)

                    val phoneCursor = requireActivity().contentResolver.query(phoneURI, phoneNumberQueryFields, phoneWhere, phoneQueryParam, null)
                    phoneCursor?.use { cursorPhone ->
                        cursorPhone.moveToFirst()
                        val phoneNum = cursorPhone.getString(0)
                        Log.d(DEBUG, "RECV: $phoneNum")
                    }
                }
            }

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

        invitationEditText.apply {
            isFocusable = false
            val intent = Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI)

            setOnClickListener {
                startActivityForResult(intent, CONTACT)
            }

//            val packageManager: PackageManager = requireActivity().packageManager
//            val resolvedAct: ResolveInfo? = packageManager.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY)
//
//            if(resolvedAct == null) {
//                isEnabled = false
//            }
        }

        locationEditText.setOnClickListener {

        }

        submitButton.setOnClickListener {
            if(startDateEditText.text.isEmpty() || endDateEditText.text.isEmpty()
                || nameEditText.text.isEmpty()) {
                Toast.makeText(context, "Complete Fields...", Toast.LENGTH_SHORT).show()
            } else {
                // TODO: write to db
            }

            Log.d(DEBUG, "Submit button worked")

        }

        startDateEditText.setText(appointmentConfigureViewModel.startDate)
        endDateEditText.setText(appointmentConfigureViewModel.endDate)

        Log.d(DEBUG, "${appointmentConfigureViewModel.startDate}, ${appointmentConfigureViewModel.endDate}")

    }

    override fun onAttach( context: Context){
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