package com.wzp.androidknowledge.broadCast.dynamicRegister

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

/**
 * Description: 动态注册广播接收者
 * Created by zhipingwang on 2024/1/9 16:31.
 */
class DynamicReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.i("wzp", "MyReceiver dynamic intent = " + intent?.action)
        if (intent == null) {
            return
        }
        if (intent.action == Intent.ACTION_POWER_DISCONNECTED) {
            Toast.makeText(context, "电源已移除", Toast.LENGTH_SHORT).show()
        }
        if (intent.action == Intent.ACTION_POWER_CONNECTED) {
            Toast.makeText(context, "电源已连接", Toast.LENGTH_SHORT).show()
        }
    }
}