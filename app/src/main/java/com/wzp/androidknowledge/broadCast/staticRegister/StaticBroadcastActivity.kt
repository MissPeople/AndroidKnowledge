package com.wzp.androidknowledge.broadCast.staticRegister

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.wzp.androidknowledge.databinding.ActivityStaticBroadcastBinding

/**
 * Description: 发送静态广播
 * Created by zhipingwang on 2024/1/9 11:00.
 */
class StaticBroadcastActivity : AppCompatActivity() {
    private val binding by lazy { ActivityStaticBroadcastBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        //两种静态注册方式都可

        /*binding.send.setOnClickListener {
            val intent = Intent("com.wzp.test")
            intent.setComponent(ComponentName(this,StaticReceiver::class.java))
            sendBroadcast(intent)
        }*/
        binding.send.setOnClickListener {
            val intent = Intent(this, StaticReceiver::class.java)
            intent.action = "com.wzp.test"
            sendBroadcast(intent)
        }
    }
}