package com.wzp.androidknowledge.broadCast.otherBroadcast

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.wzp.androidknowledge.databinding.ActivityOtherBroadcastBinding


/**
 * Description: 其他分类广播
 * Created by zhipingwang on 2024/1/9 17:17.
 */
class OtherBroadcast : AppCompatActivity() {
    private val binding by lazy { ActivityOtherBroadcastBinding.inflate(layoutInflater) }
    private lateinit var receiver: OtherReceiver
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        binding.send.setOnClickListener {
            val intent = Intent("com.wzp.custom")
            intent.putExtra("extra_key", "extra_value")
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        val intentFilter = IntentFilter("com.wzp.custom")
        val receiver = OtherReceiver()
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, intentFilter)
    }

    override fun onDestroy() {
        super.onDestroy()
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver)
    }
}