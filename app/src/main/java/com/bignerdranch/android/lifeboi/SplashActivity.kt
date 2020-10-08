package com.bignerdranch.android.lifeboi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

private const val TAG = "SplashActivity"
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