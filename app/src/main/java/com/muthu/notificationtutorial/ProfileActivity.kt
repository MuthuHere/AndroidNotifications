package com.muthu.notificationtutorial

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessaging
import kotlin.properties.Delegates

class ProfileActivity : AppCompatActivity() {

    var auth: FirebaseAuth by Delegates.notNull()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)


        //init auth
        auth = FirebaseAuth.getInstance()

        //init topic
        FirebaseMessaging.getInstance().subscribeToTopic("myUpdate")

        //to get fireBase ID
        getFireBaseToken()
    }


    private fun getFireBaseToken() {

        FirebaseInstanceId.getInstance().instanceId.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                saveToken(task.result?.token ?: "NULL_TOKEN")
                Log.i("M_TOKEN==>", "" + task.result?.token)
            }
        }

    }

    private fun saveToken(token: String) {
        val email = auth.currentUser?.email
        if (!email.isNullOrEmpty()) {
            var user = User(email, token)

            val dbUser = FirebaseDatabase.getInstance().getReference("users")

            dbUser.child(auth.currentUser?.uid!!).setValue(user).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this@ProfileActivity, "Token Saved", Toast.LENGTH_SHORT).show()

                }
            }


        }
    }

    override fun onStart() {
        super.onStart()
        if (auth.currentUser == null) {
            gotoMainActivity()
        }
    }


    private fun gotoMainActivity() {
        startActivity(
            Intent(this, MainActivity::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        )

    }

}
