package com.bignerdranch.android.lifeboi


import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.bignerdranch.android.lifeboi.database.FirebaseClient
import com.bignerdranch.android.lifeboi.datamodel.Appointment
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout
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

    private lateinit var appointmentConstant: Appointment
    private lateinit var appointmentHost: Appointment
    private lateinit var appointmentInvitee1: Appointment
    private lateinit var appointmentInvitee2: Appointment
    private lateinit var appointmentInvitee3: Appointment


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

        appointmentConstant = Appointment(
            id = "always_stays",
            host = "foobar_constant",
            name = "Test Event",
            startDate = "10/12/2020",
            endDate = "10/12/2020",
            phoneNumber = "0000000000",
            invitee = false
        )

        appointmentHost = Appointment(
            id = "for_testing_purposes",
            host = "foobar",
            name = "Test Event",
            invitations = listOf("foobar1", "foobar2", "foobar3"),
            startDate = "10/12/2020",
            endDate = "10/12/2020",
            phoneNumber = "1235551234",
            invitee = false
        )

        appointmentInvitee1 = Appointment(
            id = "for_testing_purposes",
            host = "foobar1",
            name = "Test Event",
            startDate = "10/12/2020",
            endDate = "10/12/2020",
            phoneNumber = "1225551234",
            invitee = true
        )

        appointmentInvitee2 = Appointment(
            id = "for_testing_purposes",
            host = "foobar2",
            name = "Test Event",
            startDate = "10/12/2020",
            endDate = "10/12/2020",
            phoneNumber = "1335551234",
            invitee = true
        )

        appointmentInvitee3 = Appointment(
            id = "for_testing_purposes",
            host = "foobar3",
            name = "Test Event",
            startDate = "10/12/2020",
            endDate = "10/12/2020",
            phoneNumber = "1245551234",
            invitee = true
        )
    }

    suspend fun loginSync(username: String, password: String): Boolean =
        suspendCoroutine { result ->
            firebaseClient.checkLoginPassword(
                username,
                password
            ) { result.resume(it) }
        }

    suspend fun checkingExistingUserSync(username: String, phoneNumber: String): Boolean =
        suspendCoroutine { result ->
            firebaseClient.checkForExistingUser(
                username,
                phoneNumber
            ) { result.resume(it) }
        }

    suspend fun getUsernameSync(phoneNumber: String): String =
        suspendCoroutine { result -> firebaseClient.getUsername(phoneNumber) { result.resume(it) } }

    suspend fun getAppointmentSync(username: String): Appointment =
        suspendCoroutine { result -> firebaseClient.getAppointment(username) { result.resume(it) } }

    suspend fun deleteAppointmentSync(
        id: String,
        phoneNumber: String,
        invitee: Boolean
    ): Appointment =
        suspendCoroutine { result -> firebaseClient.deleteAppointment(id, phoneNumber, invitee) }

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

    @Test
    fun testGetNoAppointment() {
        runBlocking {
            val response = getAppointmentSync("foobar_none")

            assertEquals("empty", response.id)
        }
    }

    @Test
    fun testAddAppointment() {
        runBlocking {
            firebaseClient.addAppointment(appointmentHost)
            firebaseClient.addAppointment(appointmentInvitee1)
            firebaseClient.addAppointment(appointmentInvitee2)
            firebaseClient.addAppointment(appointmentInvitee3)

            val response = getAppointmentSync("foobar")
            var responseInvitee1 = getAppointmentSync("foobar1")
            var responseInvitee2 = getAppointmentSync("foobar2")
            var responseInvitee3 = getAppointmentSync("foobar3")

            assertEquals("for_testing_purposes", response.id)
            assertEquals("for_testing_purposes", responseInvitee1.id)
            assertEquals("for_testing_purposes", responseInvitee2.id)
            assertEquals("for_testing_purposes", responseInvitee3.id)
        }
    }


    @Test
    fun testHostDeleteAppointment() {
        firebaseClient.deleteAppointment(appointmentHost.id, appointmentHost.phoneNumber, appointmentHost.invitee)
        runBlocking {

            assertNotNull(getAppointmentSync("foobar"))

        }
    }


}