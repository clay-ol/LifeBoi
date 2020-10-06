package com.bignerdranch.android.lifeboi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

private const val REQUEST_HOME_SCREEN = 0

class SplashActivity : AppCompatActivity() {

    private lateinit var loginButton: Button
    private lateinit var signUpButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loginButton = findViewById(R.id.login_button)
        signUpButton = findViewById(R.id.signup_button)

        loginButton.setOnClickListener {
            val intent = HomeActivity.newIntent(this@SplashActivity)
            startActivityForResult(intent, REQUEST_HOME_SCREEN)
        }

        signUpButton.setOnClickListener {
            val intent = HomeActivity.newIntent(this@SplashActivity)
            startActivityForResult(intent, REQUEST_HOME_SCREEN)
        }
    }
}