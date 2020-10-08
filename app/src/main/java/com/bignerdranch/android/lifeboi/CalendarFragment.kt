package com.bignerdranch.android.lifeboi

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import androidx.fragment.app.Fragment

private const val TAG = "CalendarFragment"

class CalendarFragment : Fragment() {

    interface Callbacks {
        fun onDateSelected()
    }

    private lateinit var appointmentCalendar: CalendarView

    private var callbacks: Callbacks? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(DEBUG, "CalendarFragment onCreate()")
    }

    override fun onAttach( context: Context){
        super.onAttach(context)
        callbacks = context as Callbacks?
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
        val view = inflater.inflate(R.layout.fragment_calendar, container, false)

        appointmentCalendar = view.findViewById(R.id.appointmentCalendar) as CalendarView
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        appointmentCalendar?.setOnDateChangeListener {view, year, month, dayOfMonth ->
            Log.d(DEBUG, "TEST")
            callbacks?.onDateSelected()
        }
    }

    companion object {
        fun newInstance() : CalendarFragment {
            return CalendarFragment()
        }
    }
}