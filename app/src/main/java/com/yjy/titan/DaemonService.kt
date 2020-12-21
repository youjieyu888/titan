package com.yjy.titan

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.os.Process
import android.util.Log

class DaemonService : Service() {

    override fun onCreate(){
        Log.d(MainActivity.TAG, "process id:"+ Process.myPid())
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}