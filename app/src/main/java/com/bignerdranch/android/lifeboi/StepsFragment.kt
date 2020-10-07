package com.bignerdranch.android.lifeboi

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

private const val TAG = "StepsFragment"

class StepsFragment : Fragment() {
    private lateinit var stepsView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_steps, container, false)
        stepsView = view.findViewById( R.id.steps ) as TextView
        return view
    }
    override fun onStart() {
        super.onStart()

    }
}