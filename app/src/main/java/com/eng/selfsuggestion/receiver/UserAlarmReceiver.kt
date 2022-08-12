package com.eng.selfsuggestion.receiver

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings.System.getString
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import com.eng.selfsuggestion.MainActivity
import com.eng.selfsuggestion.R

class UserAlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
        TODO("UserAlarmReceiver.onReceive() is not implemented")
        val CHANNEL_ID = "UserAlarmTime"
        val NOTIFICATION_ID = 0
        var notificationManager: NotificationManager? = null
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, 0)

        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = R.string.channel_name
            val descriptionText = R.string.channel_description
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name.toString(), importance).apply {
                description = descriptionText.toString()
            }
            // Register the channel with the system
            notificationManager =
                Context.NOTIFICATION_SERVICE as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        // Create an explicit intent for an Activity in your app
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.googleg_disabled_color_18)
            .setContentTitle("My notification")
            .setContentText("Hello World!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            // Set the intent that will fire when the user taps the notification
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        notificationManager?.notify(NOTIFICATION_ID,builder.build())


    }


}