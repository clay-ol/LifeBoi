package com.bignerdranch.android.lifeboi.database

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import java.util.concurrent.Executors


class FirebaseClient private constructor(context: Context) {

    private val database = Firebase.firestore

    var users: MutableLiveData<UserAccount> = MutableLiveData()

    private val executor = Executors.newSingleThreadExecutor()


    fun checkLoginPassword(username: String, password: String, callback:(Boolean) -> Unit
    ) {
        database.collection("users").document(username)
            .get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    if (document.get("password")?.equals(password)!!) {
                        callback.invoke(true)

                    } else {
                        callback.invoke(false)
                    }


                } else {
                    Log.d("SplashActivity", "No such document")
                    callback.invoke(false)
                }
            }
            .addOnFailureListener{ exception ->
                Log.d("SplashActivity", "got failed with ", exception)
            }

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