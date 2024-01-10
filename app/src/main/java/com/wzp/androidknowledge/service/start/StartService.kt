package com.wzp.androidknowledge.service.start

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log


/**
 * Description: service
 * Created by zhipingwang on 2024/1/10 10:09.
 */
class StartService : Service() {

    private val TAG = "WZP"

    override fun onCreate() {
        super.onCreate()
        Log.i(TAG,"service onCreate!")
    }

    override fun onStartCommand(intent: Intent?, flags: Int,startId: Int): Int {
        Log.i(TAG,"service onStartCommand!")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG,"service onDestroy!")
    }
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}