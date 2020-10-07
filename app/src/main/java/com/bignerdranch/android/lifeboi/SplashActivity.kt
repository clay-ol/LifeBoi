package com.bignerdranch.android.lifeboi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.bignerdranch.android.lifeboi.database.UserAccount
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

const val DEBUG = "LIFEBOI"
private const val TAG = "SplashActivity"
private const val REQUEST_HOME_SCREEN = 0

class SplashActivity : AppCompatActivity() {

    private lateinit var loginButton: Button
    private lateinit var signUpButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FirebaseApp.initializeApp(this)

        val database = Firebase.database
        val myRef = database.getReference("users")
        val usersRef = myRef.child("johndoe")

        loginButton = findViewById(R.id.login_button)
        signUpButton = findViewById(R.id.signup_button)

        loginButton.setOnClickListener {
            val intent = HomeActivity.newIntent(this@SplashActivity)
            startActivityForResult(intent, REQUEST_HOME_SCREEN)
        }

        signUpButton.setOnClickListener {
            usersRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    val value = dataSnapshot.getValue<UserAccount>()
                    Log.d(TAG, "Value is: $value")
                }

                override fun onCancelled(error: DatabaseError) {
                    // Failed to read value
                    Log.w(TAG, "Failed to read value.", error.toException())
                }
            })
            //val intent = HomeActivity.newIntent(this@SplashActivity)


            //startActivityForResult(intent, REQUEST_HOME_SCREEN)



        }
    }
}