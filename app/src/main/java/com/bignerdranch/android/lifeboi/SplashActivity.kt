package com.bignerdranch.android.lifeboi

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.Manifest.permission.READ_CONTACTS
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat


const val DEBUG = "LIFEBOI"
private const val TAG = "SplashActivity"
private const val PERMISSION_REQUEST_CODE = 1
private const val PERMISSION_REQUEST_CODE_GPS = 2
private const val REQUEST_HOME_SCREEN = 0


class SplashActivity : AppCompatActivity(), SplashFragment.Callbacks, LoginFragment.Callbacks, SignUpFragment.Callbacks {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val currentFragment =
            supportFragmentManager.findFragmentById(R.id.splash_fragment_container)

        if (currentFragment == null) {
            val fragment = SplashFragment.newInstance()
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.splash_fragment_container, fragment)
                .commit()
        }

        if(!checkPermission()) {
            requestPermission()
        }

        if(!checkGPSPermission()) {
            requestGPSPermission()
        }

    }

    private fun checkGPSPermission(): Boolean {
        val result = ContextCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION)
        return result == PackageManager.PERMISSION_GRANTED
    }

    private fun requestGPSPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                ACCESS_FINE_LOCATION
            )
        ) {
            Toast.makeText(this, "read contact permission.", Toast.LENGTH_LONG).show()
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(ACCESS_FINE_LOCATION),
                PERMISSION_REQUEST_CODE_GPS
            )
        }
    }

    private fun requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                READ_CONTACTS
            )
        ) {
            Toast.makeText(this, "read contact permission.", Toast.LENGTH_LONG).show()
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(READ_CONTACTS),
                PERMISSION_REQUEST_CODE
            )
        }
    }

    private fun checkPermission(): Boolean {
        val result = ContextCompat.checkSelfPermission(this, READ_CONTACTS)
        return result == PackageManager.PERMISSION_GRANTED
    }

    override fun goToLogin() {
        val fragment = LoginFragment.newInstance()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.splash_fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun goToSignUp() {
        val fragment = SignUpFragment.newInstance()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.splash_fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun goToSplash() {
        val fragment = SplashFragment.newInstance()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.splash_fragment_container, fragment)
            .commit()
    }
}