package com.bignerdranch.android.lifeboi

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.bignerdranch.android.lifeboi.viewModels.FitnessViewModel


private const val TAG = "StepsFragment"
private const val STEPS_ARG_USERNAME = "username"
private const val PUSHUP_STATE = "pushup"
private const val PULLUP_STATE = "pullup"
private const val SQUAT_STATE = "squat"
private const val LEGLIFT_STATE = "leglift"

class FitnessFragment : Fragment() {

    private var username = ""
    private lateinit var pushupBox: CheckBox
    private lateinit var pullupBox: CheckBox
    private lateinit var squatBox: CheckBox
    private lateinit var legliftBox: CheckBox
    private lateinit var fitnessViewModel: FitnessViewModel
//    private val fitnessViewModel: FitnessViewModel by lazy {
//        ViewModelProviders.of(this).get(FitnessViewModel::class.java)
//    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fitnessViewModel = activity?.let { ViewModelProviders.of(it).get( FitnessViewModel::class.java) }!!
//        if( arguments != null ){
//            fitnessViewModel.pushupState = arguments?.getSerializable(PUSHUP_STATE) as Boolean
//            fitnessViewModel.pullupState = arguments?.getSerializable(PULLUP_STATE) as Boolean
//            fitnessViewModel.squatState = arguments?.getSerializable(SQUAT_STATE) as Boolean
//            fitnessViewModel.legliftState = arguments?.getSerializable(LEGLIFT_STATE) as Boolean
//            Log.d( TAG, "set checkbox states" )
//        }


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_fitness, container, false)
        pushupBox = view.findViewById( R.id.pushups )
        pullupBox = view.findViewById( R.id.pullups )
        squatBox = view.findViewById( R.id.squats )
        legliftBox = view.findViewById( R.id.leglifts )

        pushupBox.isChecked = fitnessViewModel.pushupState
        pullupBox.isChecked = fitnessViewModel.pullupState
        squatBox.isChecked = fitnessViewModel.squatState
        legliftBox.isChecked = fitnessViewModel.legliftState

        pushupBox.setOnClickListener{ view:View ->
            markExercise( 1, pushupBox.isChecked )
        }
        pullupBox.setOnClickListener { view:View ->
            markExercise( 2, pullupBox.isChecked )
        }
        squatBox.setOnClickListener {  view:View ->
            markExercise( 3, squatBox.isChecked )
        }
        legliftBox.setOnClickListener { view:View ->
            markExercise( 4, legliftBox.isChecked )
        }
        complete()

        return view
    }
    override fun onStart() {
        super.onStart()

    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean( PUSHUP_STATE, pushupBox.isChecked )
        outState.putBoolean( PULLUP_STATE, pullupBox.isChecked )
        outState.putBoolean( SQUAT_STATE, squatBox.isChecked )
        outState.putBoolean( LEGLIFT_STATE, legliftBox.isChecked )
    }

    private fun markExercise( exerciseNo: Int, mark: Boolean ){
        when {
            exerciseNo == 1 -> {
                fitnessViewModel.pushupState = mark
            }
            exerciseNo == 2 -> {
                fitnessViewModel.pullupState = mark
            }
            exerciseNo == 3 -> {
                fitnessViewModel.squatState = mark
            }
            exerciseNo == 4 -> {
                fitnessViewModel.legliftState = mark
            }

        }
        complete()
        Log.d( TAG,"Exercise marked: ${mark}")

    }

    private fun complete(){
        if( fitnessViewModel.pushupState &&
                fitnessViewModel.pullupState &&
                fitnessViewModel.squatState &&
                fitnessViewModel.legliftState ){
            Toast.makeText( context, R.string.fitnessToast, Toast.LENGTH_SHORT )
                .show()
        }
    }


    companion object {
        fun newInstance(username: String): FitnessFragment {
            val args = Bundle().apply {
//                pustSerializable(STEPS_ARG_USERNAME, username)
            }

            return FitnessFragment().apply{
                arguments = args
            }
        }
    }
}