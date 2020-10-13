package com.bignerdranch.android.lifeboi

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.lifeboi.database.FirebaseClient
import com.bignerdranch.android.lifeboi.datamodel.Appointment
import com.bignerdranch.android.lifeboi.viewModels.AppointmentListViewModel
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions


private const val TAG = "AppointmentListFragment"
private const val ARG_USERNAME = "username"

class AppointmentListFragment : Fragment() {

    interface Callbacks {
        fun onAddSelected()
        fun onEditSelected(UUID: String)
    }

    private lateinit var appointmentRecyclerView: RecyclerView
    private lateinit var appointmentButton: ImageButton

    private lateinit var adapter: AppointmentAdapter
    private var callbacks: Callbacks? = null

    private var username = ""

    override fun onAttach(context: Context){
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

        Log.d(DEBUG, "appointmentlist")
        Log.d(TAG, "Got username: ${username}")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_appointment_list, container, false)

        appointmentButton = view.findViewById(R.id.configure_appointment) as ImageButton
        appointmentRecyclerView = view.findViewById(R.id.appoint_reycler_view) as RecyclerView
        appointmentRecyclerView.layoutManager = LinearLayoutManager(context)

        updateUI()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        appointmentButton.setOnClickListener {
            Log.d(DEBUG, "Switching fragments: AppointmentList -> Calendar")
            callbacks?.onAddSelected()
        }
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.startListening()
    }

    private fun updateUI() {

        val db = FirebaseClient.get().getDatabase()
        Log.d("FirebaseClient", "RecyclerView Username: $username")
        val query = db.collection("appointments").whereEqualTo("host", username)

        val options: FirestoreRecyclerOptions<Appointment> = FirestoreRecyclerOptions.Builder<Appointment>()
            .setLifecycleOwner(this)
            .setQuery(query, Appointment::class.java)
            .build()

        adapter = AppointmentAdapter(options)
        appointmentRecyclerView.adapter = adapter
    }


    private inner class AppointmentAdapter(var appoints: FirestoreRecyclerOptions<Appointment>) : FirestoreRecyclerAdapter<Appointment, AppointmentHolder>(appoints) {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppointmentHolder {
            val view = layoutInflater.inflate(R.layout.list_item_appointment, parent, false)
            return AppointmentHolder(view)
        }

        override fun onBindViewHolder(holder: AppointmentHolder, position: Int, appointment: Appointment) {
            holder.bind(appointment)
        }
    }

    private inner class AppointmentHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        private lateinit var appointment: Appointment

        private val appointmentTextView: TextView = itemView.findViewById(R.id.appointment_text)
        private val appointmentLinearLayout: LinearLayout = itemView.findViewById(R.id.item_list_appointment)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            callbacks?.onEditSelected(appointment.id)
        }

        fun bind(appointment: Appointment) {
            this.appointment = appointment
            appointmentTextView.text = this.appointment.name
            if(this.appointment.isInvitee) {
                appointmentLinearLayout.setBackgroundResource(R.drawable.border)
            } else {
                appointmentLinearLayout.setBackgroundResource(R.drawable.border_host)
            }
        }
    }

    companion object {
        fun newInstance(username: String) : AppointmentListFragment {
            val args = Bundle().apply {
                putSerializable(ARG_USERNAME, username)
            }
            return AppointmentListFragment().apply {
                arguments = args
            }
        }
    }
}