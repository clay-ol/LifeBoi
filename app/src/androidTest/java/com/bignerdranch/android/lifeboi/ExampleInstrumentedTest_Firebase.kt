package com.bignerdranch.android.lifeboi


import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.bignerdranch.android.lifeboi.database.FirebaseClient
import com.bignerdranch.android.lifeboi.datamodel.Appointment
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest_Firebase {
    
    private lateinit var firebaseClient: FirebaseClient
    private lateinit var userAccount: HashMap<String, String>
    private lateinit var appointment: Appointment

    @Before
    fun setUp() {
        FirebaseClient.initialize(InstrumentationRegistry.getInstrumentation().targetContext.applicationContext)
        firebaseClient = FirebaseClient.get()


        userAccount = hashMapOf(
            "first_name" to "Foo",
            "last_name" to "Bar",
            "email" to "foobar@gmail.com",
            "phone_number" to "foobar",
            "password" to "foobar"
        )

        appointment = Appointment(
            id = "for_testing_purposes",
            host = "foobar",
            name = "Test Event",
            invitations = listOf("johndoe", "user1", "user2"),
            startDate = "10/12/2020",
            endDate = "10/12/2020",
            phoneNumber = "1235551234"
        )
    }

    suspend fun loginSync(username: String, password: String): Boolean
            = suspendCoroutine { result -> firebaseClient.checkLoginPassword(username, password){ result.resume(it)} }

    suspend fun checkingExistingUserSync(username: String, phoneNumber: String): Boolean
            = suspendCoroutine { result -> firebaseClient.checkForExistingUser(username, phoneNumber){ result.resume(it)} }

    suspend fun getUsernameSync(phoneNumber: String): String
            = suspendCoroutine { result -> firebaseClient.getUsername(phoneNumber){ result.resume(it)} }

    @Test
    fun testSuccessfulLogin() {
        runBlocking {
            val response = loginSync("johndoe", "123")

            assertTrue(response)
        }
    }


    @Test
    fun testFailedLogin() {
        runBlocking {
            val response = loginSync("johndoe", "1234")

            assertFalse(response)
        }
    }


    @Test
    fun testForExistingUsername() {

        runBlocking {
            val response = checkingExistingUserSync("foobar", "foobar")

            assertFalse(response)
        }
    }

    
    @Test
    fun testAddingNewUser() {

        runBlocking {
            firebaseClient.addUser(userAccount, "foobar")

            val response = checkingExistingUserSync("foobar", "foobar")

            assertTrue(response)

            firebaseClient.deleteUser("foobar")

            val response2 = checkingExistingUserSync("foobar", "foobar")

            assertFalse(response2)
        }
    }


    @Test
    fun testGetUsername() {
        runBlocking {
            val response = getUsernameSync("1235551234")

            assertEquals("johndoe", response)
        }
    }




}