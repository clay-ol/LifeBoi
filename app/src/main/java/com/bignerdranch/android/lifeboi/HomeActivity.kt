package com.bignerdranch.android.lifeboi

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

const val EXTRA_USER_FOUND = "com.bignerdranch.android.lifeboi.user_found"
class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
    }

    companion object{
        fun newIntent(packageContext: Context) : Intent {
            return Intent(packageContext, HomeActivity::class.java).apply {
                putExtra(EXTRA_USER_FOUND, true)
            }
        }
    }
}