package com.wzp.androidknowledge.broadCast.staticRegister

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import com.wzp.androidknowledge.MainActivity
import com.wzp.androidknowledge.R
import com.wzp.androidknowledge.service.bind.BindService
import com.wzp.androidknowledge.tools.Constants

/**
 * Description:静态注册广播
 * Created by zhipingwang on 2024/1/9 10:50.
 */
class StaticReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.i("wzp", "MyReceiver static intent = " + intent?.action)
        intent?.action?.apply {
            val nManager = context?.getSystemService(AppCompatActivity.NOTIFICATION_SERVICE) as NotificationManager
            val mBuilder = NotificationCompat.Builder(context,Constants.mNormalChannelId)
                .setContentTitle("普通通知")
                .setContentText("普通通知内容")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT) // 7.0设置优先级
                .setAutoCancel(true)
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
                val channel = NotificationChannel(
                    Constants.mNormalChannelId,
                    Constants.mNormalChannelName,
                    NotificationManager.IMPORTANCE_LOW
                ).apply {
                    description = "描述"
                    setShowBadge(false) // 是否在桌面显示角标
                }
                nManager.createNotificationChannel(channel)
            }
            nManager.notify(Constants.mNormalNotificationId,mBuilder.build())
            context.startService(Intent(context,BindService::class.java))
        }
    }
}