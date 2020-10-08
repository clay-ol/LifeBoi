package com.bignerdranch.android.lifeboi

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.lifeboi.datamodel.Appointment
import com.bignerdranch.android.lifeboi.viewModels.AppointmentListViewModel

private const val TAG = "AppointmentListFragment"

class AppointmentListFragment : Fragment() {

    private lateinit var appointmentRecyclerView: RecyclerView
//    private lateinit var appointmentRelativeLayout: RelativeLayout
    private lateinit var appointmentButton: Button
    private var adapter: AppointmentAdapter? = null

    // TEMPORARY
    private val appointmentListViewModel: AppointmentListViewModel by lazy {
        ViewModelProviders.of(this).get(AppointmentListViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(DEBUG, "appointmentlist")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_appointment_list, container, false)

        Log.d(DEBUG, "HERE")
//        appointmentRelativeLayout = view.findViewById(R.id.appointment_list_manager) as RelativeLayout
        appointmentButton = view.findViewById(R.id.configure_appointment) as Button
        appointmentRecyclerView = view.findViewById(R.id.appoint_reycler_view) as RecyclerView
        appointmentRecyclerView.layoutManager = LinearLayoutManager(context)
        Log.d(DEBUG, "Made it")

        updateUI()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun updateUI() {
        val appointments = appointmentListViewModel.appointments
        adapter = AppointmentAdapter(appointments)
        appointmentRecyclerView.adapter = adapter
    }

    private inner class AppointmentAdapter(var appoints: List<Appointment>) : RecyclerView.Adapter<AppointmentHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppointmentHolder {
            val view = layoutInflater.inflate(R.layout.list_item_appointment, parent, false)
            return AppointmentHolder(view)
        }

        override fun getItemCount(): Int {
            return appoints.size
        }

        override fun onBindViewHolder(holder: AppointmentHolder, position: Int) {
            val appointment = appoints[position]
            holder.bind(appointment)
        }
    }

    private inner class AppointmentHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        private lateinit var appointment: Appointment

        private val appointmentTextView: TextView = itemView.findViewById(R.id.appointment_text)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            Toast.makeText(context, "TEST", Toast.LENGTH_SHORT).show()
        }

        fun bind(appointment: Appointment) {
            this.appointment = appointment
            appointmentTextView.text = this.appointment.name
        }
    }

    companion object {
        fun newInstance() : AppointmentListFragment {
            return AppointmentListFragment()
        }
    }
}