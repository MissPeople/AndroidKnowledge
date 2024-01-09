package com.wzp.androidknowledge.broadCast.dynamicRegister

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

/**
 * Description: 动态注册广播接收者
 * Created by zhipingwang on 2024/1/9 16:31.
 */
class DynamicReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.i("wzp", "MyReceiver dynamic intent = " + intent?.action)
    }
}