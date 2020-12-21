package com.yjy.titan

import android.app.*
import android.content.Context
import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Process.myPid
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.core.app.JobIntentService
import com.yjy.titan.MyIntentService.Companion.INTENT_SERVICE_KEY
import com.yjy.titan.gui.ViewController

class MainActivity : AppCompatActivity() {

//    val viewController : ViewController

    val rootView get() = window.decorView.rootView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d(TAG, "process id:"+myPid())

        initTextView()

        createNotificationChannel()

        val serviceBtn = findViewById<Button>(R.id.intent_service_btn)
        serviceBtn.setOnClickListener {
            Intent(this, MyIntentService::class.java).let{
                it.putExtra(INTENT_SERVICE_KEY, "NOTIFICATION")
                JobIntentService.enqueueWork(this, MyIntentService::class.java, 1,it)
            }
        }
    }

    private fun initTextView() {
        val tv:TextView = findViewById(R.id.intent_data)
        val uri = intent.data
        if(uri!=null){
            tv.text =uri.authority
        }
    }

    private fun createNotificationChannel() {
        val name = getString(R.string.channel_name)
        val descriptionText = getString(R.string.channel_description)
        val importance = NotificationManager.IMPORTANCE_MAX
        val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
            description = descriptionText
            vibrationPattern = LongArray(0)
        }
        // Register the channel with the system
        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    companion object {
        val TAG = "Titan"
        val CHANNEL_ID = "intentService"
    }
}