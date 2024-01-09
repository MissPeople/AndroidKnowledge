package com.wzp.androidknowledge.broadCast

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.wzp.androidknowledge.RecycleViewAdapter
import com.wzp.androidknowledge.broadCast.dynamicRegister.DynamicBroadcastActivity
import com.wzp.androidknowledge.broadCast.otherBroadcast.OtherBroadcast
import com.wzp.androidknowledge.broadCast.staticRegister.StaticBroadcastActivity
import com.wzp.androidknowledge.databinding.ActivityBroadcastBinding
import com.wzp.androidknowledge.databinding.ActivityMainBinding
import com.wzp.androidknowledge.notification.NotificationActivity
import java.util.HashMap

class BroadcastActivity : AppCompatActivity() {
    private val binding by lazy { ActivityBroadcastBinding.inflate(layoutInflater) }
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
                startActivity(Intent(this@BroadcastActivity,list[it]))
            }
        }
    }

    private fun generateData() :Map<String,Class<*>>{
        val map = HashMap<String, Class<*>>()
        map["静态广播"] = StaticBroadcastActivity::class.java
        map["动态广播"] = DynamicBroadcastActivity::class.java
        map["其他广播"] = OtherBroadcast::class.java
        return map
    }
}