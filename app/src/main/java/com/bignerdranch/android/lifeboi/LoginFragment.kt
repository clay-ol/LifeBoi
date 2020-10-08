package com.bignerdranch.android.lifeboi

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment

class LoginFragment: Fragment() {

    private lateinit var username: String
    private lateinit var password: String
    private lateinit var userNameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var backButton: Button

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
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        userNameEditText = view.findViewById(R.id.username_edit_text) as EditText
        passwordEditText = view.findViewById(R.id.password_edit_text) as EditText
        loginButton = view.findViewById(R.id.login_confirm_button)
        backButton = view.findViewById(R.id.back_to_splash_button)
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
                //crime.title = sequence.toString()
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
        passwordEditText.addTextChangedListener(passwordWatcher)

        loginButton.setOnClickListener {
            Log.d("SplashActivity", username)
            Log.d("SplashActivity", password)
        }

        backButton.setOnClickListener {
            callbacks?.goToSplash()
        }
    }

    companion object {
        fun newInstance(): LoginFragment {
            return LoginFragment()
        }
    }
}