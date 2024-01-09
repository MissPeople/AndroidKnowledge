package com.wzp.androidknowledge.broadCast.dynamicRegister

import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.wzp.androidknowledge.databinding.ActivityDynamicBroadcastBinding

/**
 * Description: 动态注册广播
 * Created by zhipingwang on 2024/1/9 16:04.
 */
class DynamicBroadcastActivity : AppCompatActivity() {
    private val binding by lazy { ActivityDynamicBroadcastBinding.inflate(layoutInflater) }
    private lateinit var intentFilter: IntentFilter
    private lateinit var receiver: DynamicReceiver
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        binding.send.setOnClickListener {
            sendBroadcast(Intent("com.wzp.dynamicBroadcast"))
        }
    }

    override fun onResume() {
        super.onResume()
        receiver = DynamicReceiver()
        intentFilter = IntentFilter()
        intentFilter.addAction("com.wzp.dynamicBroadcast")
        intentFilter.addAction(Intent.ACTION_POWER_DISCONNECTED)
        intentFilter.addAction(Intent.ACTION_POWER_CONNECTED)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(receiver, intentFilter, RECEIVER_EXPORTED)
        } else {
            registerReceiver(receiver, intentFilter)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        if (receiver.isInitialStickyBroadcast) {
            unregisterReceiver(receiver)
        }
    }
}