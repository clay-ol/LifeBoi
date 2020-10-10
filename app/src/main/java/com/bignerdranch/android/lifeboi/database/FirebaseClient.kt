package com.bignerdranch.android.lifeboi.database

import android.content.Context
import android.renderscript.Script
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.bignerdranch.android.lifeboi.datamodel.Appointment
import com.bignerdranch.android.lifeboi.datamodel.UserAccount
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*
import java.util.concurrent.Executors
import kotlin.collections.HashMap


class FirebaseClient private constructor(context: Context) {

    private val TAG = "FirebaseClient"
    private val database = Firebase.firestore

    var users: MutableLiveData<UserAccount> = MutableLiveData()

    private val executor = Executors.newSingleThreadExecutor()

    fun getDatabase() : FirebaseFirestore {
        return database
    }
    
    fun signUp(data: HashMap<String, String>, username: String) {
        database.collection("users").document(username)
            .set(data)
            .addOnSuccessListener {
                Log.d(TAG, "Sign Up Successful!")
            }
            .addOnFailureListener{e ->
                Log.d(TAG, "Sign Up Error", e)
            }
    }

    fun checkForExistingUser(username: String, phonenumber: String, callback: (Boolean) -> Unit) {
        database.collection("users").document(username)
            .get()
            .addOnSuccessListener { document ->
                if (!document.exists()) {
                    if (document.getString("phone_number").equals(phonenumber)) {
                        Log.d(TAG, "No Existing Username: ${username} and Phone Number: ${phonenumber} Found")
                        callback.invoke(true)

                    } else {
                        Log.d(TAG, "Unique Username: ${username}, but Existing Phone Number: ${phonenumber} Found")
                        callback.invoke(false)
                    }
                } else {
                    Log.d(TAG, "Found Existing Username: ${username}")
                    callback.invoke(false)
                }
            }

            .addOnFailureListener{ exception ->
                Log.d(TAG, "got failed with ", exception)
            }
    }


    fun checkLoginPassword(username: String, password: String, callback:(Boolean) -> Unit
    ) {
        database.collection("users").document(username)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    if (document.get("password")?.equals(password)!!) {
                        Log.d(TAG, "username and password matches")
                        callback.invoke(true)

                    } else {
                        Log.d(TAG, "found username, but incorrect password")
                        callback.invoke(false)
                    }


                } else {
                    Log.d(TAG, "No such document")
                    callback.invoke(false)
                }
            }
            .addOnFailureListener{ exception ->
                Log.d(TAG, "got failed with ", exception)
            }

    }

    fun addAppointment(data: Appointment) {
        database.collection("appointments")
            .add(data)
            .addOnSuccessListener {
                Log.d(TAG, "added appointment to database!")
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "failed to add appointment to database", e)
            }
    }

    /* TODO - figure out a way to make this work with Ted and also handle the Date issue
    fun getUserAppointments(username: String, callback: (List<Appointment>) -> Unit) {
        database.collection("appointments")
            .whereEqualTo("host", username)
            //.whereArrayContains("invitation", username)
            .get()
            .addOnSuccessListener { documents ->
                val listOfDocuments: MutableList<Appointment> = mutableListOf()
                for (document in documents) {

                    val appointment = Appointment(
                        UUID.randomUUID().toString(),
                        document.data["location"].toString(),
                        username,
                        document.data["name"].toString(),
                        document.data["invitations"] as List<String>,
                        document.data["startDate"].toString(),
                        document.data["endDate"].toString()
                    )


                    listOfDocuments.add(appointment)
                }
                Log.d(TAG, "Found Appointments!")
                callback.invoke(listOfDocuments)
            }

            .addOnFailureListener { e ->
                Log.e(TAG, "failed to get appointments", e)
            }
    }
     */
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