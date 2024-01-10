package com.wzp.androidknowledge.service.bind

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.wzp.androidknowledge.R
import com.wzp.androidknowledge.broadCast.staticRegister.StaticReceiver


/**
 * Description: bindService
 * Created by zhipingwang on 2024/1/10 10:38.
 */
class BindService : Service() {
    private val TAG = "WZP"
    private val NOTIFICATION_ID = 1
    private val CHANNEL_ID = "ForegroundServiceChannel"
    private val myBinder = MyBinder()
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate() {
        super.onCreate()
       /* createNotificationChannel()
        val notification: Notification = buildNotification()
        startForeground(NOTIFICATION_ID, notification)*/
        Log.i(TAG,"-----------onCreate------------")
    }

    override fun onDestroy() {
        super.onDestroy()
        val intent = Intent(this, StaticReceiver::class.java)
        intent.action = "com.wzp.test"
        sendBroadcast(intent)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val notification = NotificationCompat.Builder(this,CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle("Title")
            .setContentText("context")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
        startForeground(1,notification.build())
        return START_STICKY // 如果服务被意外终止，系统会尝试重新创建服务
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.i(TAG,"-----------onUnbind------------")
        return super.onUnbind(intent)
    }
    override fun onBind(intent: Intent?): IBinder {
        Log.i(TAG,"-----------onBind------------")
        return myBinder
    }
    class MyBinder :Binder() {
        fun sendNotification() {
            Log.i("WZP","-----------sendNotification------------")
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name: CharSequence = "ForegroundServiceChannel"
            val description = "Channel for Foreground Service"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance)
            channel.description = description
            val notificationManager = getSystemService(
                NotificationManager::class.java
            )
            notificationManager?.createNotificationChannel(channel)
        }
    }

    // 构建通知的方法
    private fun buildNotification(): Notification {
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle("Foreground Service")
            .setContentText("Service is running in the foreground")
            .build()
    }
}