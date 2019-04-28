package com.muthu.notificationtutorial

import android.app.NotificationChannel
import android.app.NotificationManager
import android.graphics.drawable.Icon
import android.os.Build
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import android.support.v7.app.AppCompatActivity;
import android.view.Menu
import android.view.MenuItem

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    val CHANNEL_ID = "notify_test"
    val CHANNEL_NAME = "Notification Test"
    val CHANNEL_DESC = "MuthuHere's Notifications"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        //creating channel for Android Oreo and above devices
        createChannel()


// button click event for simple notification
        btnCreateNotification.setOnClickListener {
            showNotification()
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


    /**
     * function used to show simple notification when it gets triggered
     */
    private fun showNotification() {

        val notificationCompat = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("Muthu's Notification Title")
            .setContentText("Hey Guys really I'm working!!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT).build()

        val notificationManager = NotificationManagerCompat.from(this)
            .notify(1, notificationCompat)

    }

}
