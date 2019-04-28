package com.muthu.notificationtutorial.fcm

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFireBaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(p0: RemoteMessage?) {
        super.onMessageReceived(p0)

        if (p0?.notification != null) {
            val title = p0.notification?.title ?: ""
            val body = p0.notification?.body ?: ""

            NotificationHelper.showNotification(applicationContext,title,body)
        }

    }
}