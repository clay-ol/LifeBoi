package com.bignerdranch.android.lifeboi

import android.content.Context
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
import androidx.fragment.app.Fragment
import com.bignerdranch.android.lifeboi.database.FirebaseClient

class SignUpFragment: Fragment() {

    private lateinit var username: String
    private lateinit var firstName: String
    private lateinit var lastName: String
    private lateinit var email: String
    private lateinit var phoneNumber: String
    private lateinit var password: String
    private lateinit var userNameEditText: EditText
    private lateinit var firstNameEditText: EditText
    private lateinit var lastNameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var phoneNumberEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var signUpButton: Button
    private lateinit var backButton: Button

    private lateinit var firebaseClient: FirebaseClient

    interface  Callbacks {
        fun goToSplash()
    }

    private var callbacks: Callbacks? = null // will act as my "MainActivity controller"

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks? // attaching the MainActivity to callbacks
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseClient = FirebaseClient.get()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_signup, container, false)

        userNameEditText = view.findViewById(R.id.username_edit_text_SU) as EditText
        firstNameEditText = view.findViewById(R.id.firstName_edit_text_SU) as EditText
        lastNameEditText = view.findViewById(R.id.lastName_edit_text_SU) as EditText
        emailEditText = view.findViewById(R.id.email_edit_text) as EditText
        phoneNumberEditText = view.findViewById(R.id.phone_edit_text) as EditText
        passwordEditText = view.findViewById(R.id.password_edit_text_SU) as EditText
        signUpButton = view.findViewById(R.id.signUp_confirm_button)
        backButton = view.findViewById(R.id.back_to_splash_button_SU)
        return view
    }

    override fun onStart() {
        super.onStart()

        val usernameWatcher = object : TextWatcher {

            override fun beforeTextChanged(
                sequence: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
                // This space intentionally left blank
            }

            override fun onTextChanged(
                sequence: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                username = sequence.toString()
            }

            override fun afterTextChanged(sequence: Editable?) {
                // This one too
            }
        }

        val firstNameWatcher = object : TextWatcher {

            override fun beforeTextChanged(
                sequence: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
                // This space intentionally left blank
            }

            override fun onTextChanged(
                sequence: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                firstName = sequence.toString()
            }

            override fun afterTextChanged(sequence: Editable?) {
                // This one too
            }
        }

        val lastNameWatcher = object : TextWatcher {

            override fun beforeTextChanged(
                sequence: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
                // This space intentionally left blank
            }

            override fun onTextChanged(
                sequence: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                lastName = sequence.toString()
            }

            override fun afterTextChanged(sequence: Editable?) {
                // This one too
            }
        }

        val emailWatcher = object : TextWatcher {

            override fun beforeTextChanged(
                sequence: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
                // This space intentionally left blank
            }

            override fun onTextChanged(
                sequence: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                email = sequence.toString()
            }

            override fun afterTextChanged(sequence: Editable?) {
                // This one too
            }
        }

        val phoneWatcher = object : TextWatcher {

            override fun beforeTextChanged(
                sequence: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
                // This space intentionally left blank
            }

            override fun onTextChanged(
                sequence: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                phoneNumber = sequence.toString()
            }

            override fun afterTextChanged(sequence: Editable?) {
                // This one too
            }
        }

        val passwordWatcher = object : TextWatcher {

            override fun beforeTextChanged(
                sequence: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
                // This space intentionally left blank
            }

            override fun onTextChanged(
                sequence: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                password = sequence.toString()
            }

            override fun afterTextChanged(sequence: Editable?) {
                // This one too
            }
        }

        userNameEditText.addTextChangedListener(usernameWatcher)
        firstNameEditText.addTextChangedListener(firstNameWatcher)
        lastNameEditText.addTextChangedListener(lastNameWatcher)
        emailEditText.addTextChangedListener(emailWatcher)
        phoneNumberEditText.addTextChangedListener(phoneWatcher)
        passwordEditText.addTextChangedListener(passwordWatcher)

        signUpButton.setOnClickListener {
            Log.d("SplashActivity", username)
            Log.d("SplashActivity", firstName)
            Log.d("SplashActivity", lastName)
            Log.d("SplashActivity", email)
            Log.d("SplashActivity", phoneNumber.toString())
            Log.d("SplashActivity", password)
        }

        backButton.setOnClickListener {
            callbacks?.goToSplash()
        }
    }

    companion object {
        fun newInstance(): SignUpFragment {
            return SignUpFragment()
        }
    }
}