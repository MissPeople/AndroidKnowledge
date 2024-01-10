package com.wzp.androidknowledge

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.wzp.androidknowledge.broadCast.BroadcastActivity
import com.wzp.androidknowledge.broadCast.dynamicRegister.DynamicBroadcastActivity
import com.wzp.androidknowledge.broadCast.staticRegister.StaticBroadcastActivity
import com.wzp.androidknowledge.databinding.ActivityMainBinding
import com.wzp.androidknowledge.notification.NotificationActivity
import com.wzp.androidknowledge.service.ServiceActivity
import java.util.HashMap

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        val list = generateData()
        binding.mainRv.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = RecycleViewAdapter(ArrayList(list.keys)) {
                startActivity(Intent(this@MainActivity,list[it]))
            }
        }
    }

    private fun generateData() :Map<String,Class<*>>{
        val map = HashMap<String, Class<*>>()
        map["通知"] = NotificationActivity::class.java
        map["广播"] = BroadcastActivity::class.java
        map["service"] = ServiceActivity::class.java
        return map
    }
}