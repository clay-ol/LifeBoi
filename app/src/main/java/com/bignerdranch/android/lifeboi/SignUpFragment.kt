package com.bignerdranch.android.lifeboi

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bignerdranch.android.lifeboi.database.FirebaseClient
import kotlin.collections.HashMap

private const val REQUEST_HOME_SCREEN = 0
private const val TAG = "LoginInfo"
class SignUpFragment: Fragment() {

    private var username = ""
    private var firstName = ""
    private var lastName = ""
    private var passwordVerify = ""
    private var phoneNumber = ""
    private var password = ""
    private lateinit var userNameEditText: EditText
    private lateinit var firstNameEditText: EditText
    private lateinit var lastNameEditText: EditText
    private lateinit var passwordVerifyEditText: EditText
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
        passwordVerifyEditText = view.findViewById(R.id.password_verify_edit_text) as EditText
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

        val passwordVerifyWatcher = object : TextWatcher {

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
                passwordVerify = sequence.toString()
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
        passwordVerifyEditText.addTextChangedListener(passwordVerifyWatcher)
        phoneNumberEditText.addTextChangedListener(phoneWatcher)
        passwordEditText.addTextChangedListener(passwordWatcher)

        signUpButton.setOnClickListener {

            val userAccount = hashMapOf(
                "first_name" to firstName,
                "last_name" to lastName,
                "phone_number" to phoneNumber,
                "password" to password
            )

            if (!foundEmpty(userAccount) && checkPhoneNumber(phoneNumber) && username != "foobar") {
                if (passwordMatch()) {
                    firebaseClient.checkForExistingUser(username, phoneNumber) {result ->
                        if (!result) {
                            firebaseClient.addUser(userAccount, username)
                            val intent = HomeActivity.newIntent((context), username)
                            startActivityForResult(intent, REQUEST_HOME_SCREEN)

                        } else {
                            Toast.makeText(activity, "Username or Phone Number Taken!", Toast.LENGTH_LONG).show()
                        }
                    }

                } else {
                    Toast.makeText(activity, "Passwords Do Not Match!", Toast.LENGTH_LONG).show()
                }


            } else {
                Toast.makeText(activity, "Missing Input Found or Incorrect Phone Number Length!", Toast.LENGTH_LONG).show()
            }


        }

        backButton.setOnClickListener {
            callbacks?.goToSplash()
        }
    }

    private fun foundEmpty(dataMap: HashMap<String, String>): Boolean {
        for ((value) in dataMap) {
            if (value.equals("")) {
                return true
            }
        }

        return false
    }

    private fun passwordMatch(): Boolean {
        if(password.equals(passwordVerify)) {
            return true
        }

        return false
    }

    private fun checkPhoneNumber(number: String): Boolean {
        if (number.length != 10) {
            return false
        }

        return true
    }

    companion object {
        fun newInstance(): SignUpFragment {
            return SignUpFragment()
        }
    }
}