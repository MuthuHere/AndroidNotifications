package com.muthu.notificationtutorial

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.graphics.drawable.Icon
import android.os.Build
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import android.support.v7.app.AppCompatActivity;
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.InstanceIdResult

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() {

    //notification channel
    private val CHANNEL_ID = "notify_test"
    private val CHANNEL_NAME = "Notification Test"
    private val CHANNEL_DESC = "MuthuHere's Notifications"

    //fireBase
    private var auth: FirebaseAuth by Delegates.notNull()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        //creating channel for Android Oreo and above devices
        createChannel()


        // button click event for simple notification
        btnCreateNotification.setOnClickListener {

            val email = etEmail.text.toString()
            val pwd = etPassword.text.toString()

            if (email.isNullOrEmpty() || pwd.isNullOrEmpty()) {
                Toast.makeText(this@MainActivity, "Email & Password Mandatory", Toast.LENGTH_SHORT).show()

                return@setOnClickListener
            } else {
                createFireBaseUser(email, pwd)
            }

        }


        //init FireBase Instance
        auth = FirebaseAuth.getInstance()


    }

    private fun createFireBaseUser(email: String, pwd: String) {
        progressBar.visibility = View.VISIBLE

        auth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener { task ->


            //registration success
            if (task.isSuccessful) {
                progressBar.visibility = View.GONE
                gotoProfileActivity()
            } else {
                // if email already exist
                if (task.exception is FirebaseAuthUserCollisionException) {
                    //check user login credentials
                    makeUserLogin(email, pwd)
                } else {
                    //unknown error
                    progressBar.visibility = View.GONE
                    Toast.makeText(this@MainActivity, task.exception.toString(), Toast.LENGTH_SHORT).show()

                }
            }

        }

    }


    /**
     * if user is existing user then make auto sign-in with fireBase again
     */
    private fun makeUserLogin(email: String, pwd: String) {

        auth.signInWithEmailAndPassword(email, pwd).addOnCompleteListener { task ->
            progressBar.visibility = View.GONE
            if (task.isSuccessful) {
                gotoProfileActivity()
            } else {
                Toast.makeText(this@MainActivity, task.exception.toString(), Toast.LENGTH_SHORT).show()

            }
        }

    }


    /**
     * Check the version of the device and
     * create the channel if it is Oreo or above Oreo
     * If you see inside the NotificationChannel constructor you can fine
     * that need of channelID, ChannelName and Importance instance from NotificationManager
     *
     */
    private fun createChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)
            channel.description = CHANNEL_DESC
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }



    private fun gotoProfileActivity() {
        startActivity(
            Intent(this, ProfileActivity::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        )

    }

    override fun onStart() {
        super.onStart()
        if (auth.currentUser != null) {
            gotoProfileActivity()
        }
    }
}

