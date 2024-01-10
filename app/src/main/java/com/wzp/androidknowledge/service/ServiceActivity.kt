package com.wzp.androidknowledge.service

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.wzp.androidknowledge.RecycleViewAdapter
import com.wzp.androidknowledge.broadCast.BroadcastActivity
import com.wzp.androidknowledge.databinding.ActivityServiceBinding
import com.wzp.androidknowledge.notification.NotificationActivity
import com.wzp.androidknowledge.service.bind.BindServiceActivity
import com.wzp.androidknowledge.service.start.StartServiceActivity
import java.util.HashMap

/**
 * Description: service使用
 *
 * intentService:是service的子类，不会阻塞主线程，在执行完onHandleIntent后会自行调用onDestroy
 * Created by zhipingwang on 2024/1/10 09:57.
 */
class ServiceActivity :AppCompatActivity() {
    private val binding by lazy { ActivityServiceBinding.inflate(layoutInflater) }
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
                startActivity(Intent(this@ServiceActivity,list[it]))
            }
        }
    }

    private fun generateData() :Map<String,Class<*>>{
        val map = HashMap<String, Class<*>>()
        map["bindService"] = BindServiceActivity::class.java
        map["startService"] = StartServiceActivity::class.java
        return map
    }
}