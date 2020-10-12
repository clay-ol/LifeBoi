package com.bignerdranch.android.lifeboi.database

import android.content.Context
import android.renderscript.Script
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.bignerdranch.android.lifeboi.datamodel.Appointment
import com.bignerdranch.android.lifeboi.datamodel.UserAccount
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*
import java.util.concurrent.Executors
import kotlin.collections.HashMap


class FirebaseClient private constructor(context: Context) {

    private val TAG = "FirebaseClient"
    private val database = Firebase.firestore

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


    fun checkLoginPassword(username: String, password: String, callback:(Boolean) -> Unit) {
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

    fun getPhoneNumber(username: String, callback:(String) -> Unit) {
        database.collection("users").document(username)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    callback.invoke(document.get("phone_number").toString())

                } else {
                    Log.d(TAG, "Username: $username Does Not Exist in Database")
                    callback.invoke("")
                }
            }
            .addOnFailureListener { e ->
                callback.invoke("")
                Log.d(TAG, "Failed to get Phone Number with Username: $username", e)
            }
    }

    fun getAppointment(username: String, callback:(Appointment) -> Unit) {
        val query = database.collection("appointments")
            .whereEqualTo("host", username)

        query.get()
            .addOnSuccessListener { document ->
                if (document.documents.size != 0) {
                    val firstDocument = document.documents[0]
                    val appointment = Appointment(
                        id = firstDocument.get("id").toString(),
                        location = firstDocument.get("location") as GeoPoint,
                        host = firstDocument.get("host").toString(),
                        name = firstDocument.get("name").toString(),
                        phoneNumber = firstDocument.get("phone_number").toString(),
                        startDate = firstDocument.get("startDate").toString(),
                        endDate = firstDocument.get("endDate").toString(),
                        invitations = firstDocument.get("invitations") as List<String>,
                        isInvitee = firstDocument.get("isInvitee") as Boolean
                    )

                    Log.d(TAG, "Got Appointment (Name: ${appointment.name})")
                    callback.invoke(appointment)

                } else {
                    Log.d(TAG, "No Appointments Found for User: $username")
                }
            }
            .addOnFailureListener { e ->
                Log.d(TAG, "Error Getting Appointment!", e)
            }
    }

    fun addAppointment(data: Appointment) {
        database.collection("appointments")
            .add(data)
            .addOnSuccessListener {
                Log.d(TAG, "added appointment to database successfully!")
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "failed to add appointment to database", e)
            }
    }


    fun deleteAppointment(id: String, username: String) {
        val myRef = database.collection("appointments")
            .whereEqualTo("id", id)
            .whereEqualTo("host", username)

        myRef.get()
            .addOnSuccessListener {document ->
                if (document.documents.size != 0) {
                    val targetedDocument = document.documents[0].id
                    Log.d(TAG, "Obtained the Targeted Document: $targetedDocument")

                    database.collection("appointments").document(targetedDocument)
                        .delete()
                        .addOnSuccessListener {
                            Log.d(TAG, "Deleted Document Successfully!")
                        }
                        .addOnFailureListener { e ->
                            Log.d(TAG, "Deletion Operation Failed", e)
                        }

                } else {
                    Log.d(TAG, "Unable to Find Document With ID=${id}")
                }
            }

            .addOnFailureListener { e ->
                Log.d(TAG, "Getting Appointment Failed!", e)
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