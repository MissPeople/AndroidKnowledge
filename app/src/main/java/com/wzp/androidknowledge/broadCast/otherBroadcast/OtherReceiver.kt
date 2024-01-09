package com.wzp.androidknowledge.broadCast.otherBroadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

/**
 * Description: 其他分类的广播
 * Created by zhipingwang on 2024/1/9 17:21.
 */
class OtherReceiver :BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if(intent == null) { return }
        if (intent.action != null && intent.action.equals("com.wzp.custom")) {
            // 处理接收到的应用内广播
            val extraValue = intent.getStringExtra("extra_key")
            Toast.makeText(context, extraValue, Toast.LENGTH_SHORT).show()
        }
    }
}