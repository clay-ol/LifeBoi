package com.bignerdranch.android.lifeboi.database

import android.content.Context
import android.util.Log
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import java.util.concurrent.Executors


class FirebaseClient private constructor(context: Context) {

    private val database = Firebase.database
    private val usersRef = database.getReference("users")

    private val executor = Executors.newSingleThreadExecutor()

    fun onCallback(userAccount: UserAccount?) {

    }
    fun checkUserAccount(username: String, password: String, myCallback: MyCallback) {
        val usernameRef = usersRef.child(username)
        usernameRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val value = dataSnapshot.getValue<UserAccount>()
                myCallback.onCallback(value)


            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
            }
        })

    }

    companion object {
        private var INSTANCE: FirebaseClient? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                FirebaseApp.initializeApp(context)
                INSTANCE = FirebaseClient(context)
            }
        }

        fun get(): FirebaseClient {
            return INSTANCE ?:
            throw IllegalStateException("FirebaseClient must be initialized")
        }
    }
}