package com.bignerdranch.android.lifeboi.twilioapi

import android.util.Base64
import android.util.Log
import com.bignerdranch.android.lifeboi.DEBUG
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

private val ACCOUNT_SID = ""
private val AUTH_TOKEN = ""

class TextMessenger {

    fun send(toPhoneNumber: String, message: String) {
        Log.d(DEBUG, "TEXTING: +1${toPhoneNumber}")
        Thread {
            sendSms(toPhoneNumber, message)
        }.start()
    }

    private fun sendSms(toPhoneNumber: String, message: String) {
        val userNum = toPhoneNumber
            .replace("(", "")
            .replace(")", "")
            .replace("-", "")
            .replace(" ", "")

        val client = OkHttpClient()
        val url =
            "https://api.twilio.com/2010-04-01/Accounts/$ACCOUNT_SID/SMS/Messages"
        val base64EncodedCredentials = "Basic " + Base64.encodeToString(
            ("$ACCOUNT_SID:$AUTH_TOKEN").toByteArray(),
            Base64.NO_WRAP
        )
        val body = FormBody.Builder()
            .add("From", "+18635786438")
            .add("To", "+1$userNum")
            .add("Body", message)
            .build()
        val request = Request.Builder()
            .url(url)
            .post(body)
            .header("Authorization", base64EncodedCredentials)
            .build()
        try {
            val response = client.newCall(request).execute()
            Log.d(DEBUG, "sendSms: " + response.body()?.string())
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }


        companion object {
        fun newInstance(): TextMessenger {
            return TextMessenger()
        }
    }

}

