package com.yjy.titan

import android.app.Notification
import android.app.Notification.VISIBILITY_PUBLIC
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.widget.RemoteViews
import androidx.core.app.JobIntentService
import androidx.core.app.NotificationCompat
import com.yjy.titan.MainActivity.Companion.CHANNEL_ID


class MyIntentService : JobIntentService() {

    override fun onHandleWork(intent: Intent) {
        Log.d(MainActivity.TAG, "intent service started")
        when(intent.getStringExtra(INTENT_SERVICE_KEY)){
            NOTIFICATION-> showNotification()
        }
    }

    fun showNotification(){
        val pendingIntent = Intent(this, MainActivity::class.java).let{
            PendingIntent.getActivity(this, 0, it, 0)
        }
        val notification: Notification = Notification.Builder(this, CHANNEL_ID)
            .setContentTitle(getText(R.string.notification_title))
            .setContentText(getText(R.string.notification_message))
            .setSmallIcon(R.drawable.service_icon)
            .setContentIntent(pendingIntent)
            .setTicker(getText(R.string.ticker_text))
            .setTimeoutAfter(36000_000)
            .setVisibility(VISIBILITY_PUBLIC)
            .addAction(Notification.Action(R.drawable.ic_launcher_background, "open", pendingIntent))
            .setStyle(Notification.DecoratedCustomViewStyle())
            .setCustomContentView(RemoteViews(packageName, R.layout.service_layout))
            .setCustomBigContentView(RemoteViews(packageName, R.layout.service_expand_layout))
            .build()
//        startForeground(1, notification)
        val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(1, notification)
    }

    companion object{
        val INTENT_SERVICE_KEY = "INTENT_SERVICE_KEY"
        val NOTIFICATION = "NOTIFICATION"
    }
}