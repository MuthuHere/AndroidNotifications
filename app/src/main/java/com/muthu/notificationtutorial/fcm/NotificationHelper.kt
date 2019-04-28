package com.muthu.notificationtutorial.fcm

import android.content.Context
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import com.muthu.notificationtutorial.R

open class NotificationHelper {



    /**
     * function used to show simple notification when it gets triggered
     */

    companion object {
        private val CHANNEL_ID = "notify_test"


        public fun showNotification(context: Context, title: String, body: String) {

            val notificationCompat = NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(body)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT).build()

            val notificationManager = NotificationManagerCompat.from(context)
                .notify(1, notificationCompat)

        }
    }

}