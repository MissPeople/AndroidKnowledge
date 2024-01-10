package com.wzp.androidknowledge.service.bind

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.wzp.androidknowledge.databinding.ActivityServiceBindBinding

/**
 * Description: 使用bind方法启动service
 * Created by zhipingwang on 2024/1/10 10:36.
 */
class BindServiceActivity : AppCompatActivity() {
    private val binding by lazy { ActivityServiceBindBinding.inflate(layoutInflater) }
    private lateinit var myBinder: BindService.MyBinder

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            myBinder = service as BindService.MyBinder
            myBinder.sendNotification()
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            Toast.makeText(this@BindServiceActivity, "unbind", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        binding.start.setOnClickListener {
            val intent = Intent(this, BindService::class.java)
            startService(intent)
        }
        binding.stop.setOnClickListener {
            val intent = Intent(this, BindService::class.java)
            stopService(intent)
        }
        binding.bind.setOnClickListener {
            val intent = Intent(this, BindService::class.java)
            bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
        }
        binding.unbind.setOnClickListener {
            unbindService(serviceConnection)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        /*unbindService(serviceConnection)*/
    }
}