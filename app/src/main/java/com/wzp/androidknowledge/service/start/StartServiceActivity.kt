package com.wzp.androidknowledge.service.start

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.wzp.androidknowledge.databinding.ActivityServiceStartBinding

/**
 * Description: 使用startService启动service
 *  1. 若未停止service，在多次启动service情况下，onCreate只是会执行一次。
 * Created by zhipingwang on 2024/1/10 10:03.
 */
class StartServiceActivity :AppCompatActivity() {
    private val binding by lazy { ActivityServiceStartBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.start.setOnClickListener {
            val intent = Intent(this, StartService::class.java)
            startService(intent)
        }
        binding.stop.setOnClickListener {
            val intent = Intent(this, StartService::class.java)
            stopService(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        /*val intent = Intent(this, StartService::class.java)
        stopService(intent)*/
    }
}