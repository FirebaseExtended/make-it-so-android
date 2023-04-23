package com.example.makeitso

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService

class MakeItSoMessagingService : FirebaseMessagingService() {
    /**
     * Called if the FCM registration token is updated. This may occur if the security of
     * the previous token had been compromised. This is called when the FCM registration
     * token is initially generated so this is where you would retrieve the token.
     */
    override fun onNewToken(token: String) {
        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // FCM registration token to your app server.
        Log.d("FCM","New token: $token")
    }
}