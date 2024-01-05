package com.wzp.androidknowledge.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.widget.RemoteViews
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.wzp.androidknowledge.MainActivity
import com.wzp.androidknowledge.R
import com.wzp.androidknowledge.RecycleViewAdapter
import com.wzp.androidknowledge.databinding.ActivityNotificationBinding
import com.wzp.androidknowledge.tools.Constants

class NotificationActivity : AppCompatActivity() {

    private val binding by lazy { ActivityNotificationBinding.inflate(layoutInflater) }
    private lateinit var mManager: NotificationManager
    private lateinit var mBuilder: NotificationCompat.Builder
    private lateinit var mReceiver: NotificationReceiver
    private var mFlag = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initParas()
        initView()
    }

    private fun initParas() {
        mManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
    }


    private fun initView() {
        createReceiver()
        val list = generateData()
        binding.notificationOptions.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = RecycleViewAdapter(ArrayList(list.keys)) {
                when (it) {
                    "普通通知" -> sendNormalNotification()
                    "重要通知" -> sendHighNotification()
                    "进度条通知" -> sendProgressNotification()
                    "更新进度条通知" -> updateProgressNotification()
                    "大文本通知" -> createBigTextNotification()
                    "大图片通知" -> createBigImageNotification()
                    "自定义通知" -> createCustomNotification()
                    "更新自定义通知"-> updateCustomNotification()
                }
            }
        }
    }

    private fun generateData(): Map<String, Class<*>> {
        val map = HashMap<String, Class<*>>()
        map["普通通知"] = NotificationActivity::class.java
        map["重要通知"] = NotificationActivity::class.java
        map["进度条通知"] = NotificationActivity::class.java
        map["更新进度条通知"] = NotificationActivity::class.java
        map["大文本通知"] = NotificationActivity::class.java
        map["大图片通知"] = NotificationActivity::class.java
        map["自定义通知"] = NotificationActivity::class.java
        map["更新自定义通知"] = NotificationActivity::class.java
        return map
    }


    /**
     * Description: 创建普通通知
     */
    private fun sendNormalNotification() {
        // 适配8.0以上， 创建渠道
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                Constants.mNormalChannelId,
                Constants.mNormalChannelName,
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "描述"
                setShowBadge(false) // 是否在桌面显示角标
            }
            mManager.createNotificationChannel(channel)
        }
        // 设置点击意图
        val clickIntent = Intent(this,MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this,0,clickIntent,PendingIntent.FLAG_IMMUTABLE)
        //构建通知
        mBuilder = NotificationCompat.Builder(this,Constants.mNormalChannelId)
            .setContentTitle("普通通知")
            .setContentText("普通通知内容")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setLargeIcon(BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher_round))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT) // 7.0设置优先级
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
        //发起通知
        mManager.notify(Constants.mNormalNotificationId,mBuilder.build())
    }

    /**
     * Description: 创建重要通知
     */
    private fun sendHighNotification() {
        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(Constants.mHighChannelId, Constants.mHighChannelName, NotificationManager.IMPORTANCE_HIGH)
            channel.setShowBadge(true)
            mManager.createNotificationChannel(channel)
        }
        mBuilder = NotificationCompat.Builder(this, Constants.mHighChannelId)
            .setContentTitle("重要通知")
            .setContentText("重要通知内容")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setLargeIcon(BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher_round))
            .setAutoCancel(true)
            .setNumber(999) // 自定义桌面通知数量
            .setContentIntent(pendingIntent) // 当点击除了去看看的区域外也可进行跳转
            .addAction(R.mipmap.ic_launcher, "去看看", pendingIntent)// 通知上的操作
            .setCategory(NotificationCompat.CATEGORY_MESSAGE) // 通知类别，"勿扰模式"时系统会决定要不要显示你的通知
            .setVisibility(NotificationCompat.VISIBILITY_PRIVATE) // 屏幕可见性，锁屏时，显示icon和标题，内容隐藏
        mManager.notify(Constants.mHighNotificationId, mBuilder.build())
    }

    /**
     * Description: 更新进度条通知
     * 1，更新进度：修改进度值即可
     * 2，下载完成，总进度和当前进度均设置为0，同时更新
     */
    private fun sendProgressNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(Constants.mProgressChannelId, Constants.mProgressChannelName, NotificationManager.IMPORTANCE_DEFAULT)
            mManager.createNotificationChannel(channel)
        }
        val progressMax = 100
        val progressCurrent = 30
        mBuilder = NotificationCompat.Builder(this@NotificationActivity, Constants.mProgressChannelId)
            .setContentTitle("进度通知")
            .setContentText("下载中：$progressCurrent%")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setLargeIcon(BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher_round))
            // 第3个参数indeterminate，false表示确定的进度，比如100，true表示不确定的进度，会一直显示进度动画，直到更新状态下载完成，或删除通知
            .setProgress(progressMax, progressCurrent, false)

        mManager.notify(Constants.mProgressNotificationId, mBuilder.build())
    }

    /**
     * Description: 更新进度条通知
     */
    private fun updateProgressNotification() {
        if (::mBuilder.isInitialized) {
            val progressMax = 100
            val progressCurrent = 50
            // 1.更新进度
            mBuilder.setContentText("下载中：$progressCurrent%").setProgress(progressMax, progressCurrent, false)
            // 2.下载完成
            //mBuilder.setContentText("下载完成！").setProgress(0, 0, false)
            mManager.notify(Constants.mProgressNotificationId, mBuilder.build())
            Toast.makeText(this, "已更新进度到$progressCurrent%", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "请先发一条进度条通知", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Description: 创建大文本通知
     */
    private fun createBigTextNotification() {
        val bigText =
            "A notification is a message that Android displays outside your app's UI to provide the user with reminders, communication from other people, or other timely information from your app. Users can tap the notification to open your app or take an action directly from the notification."
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(Constants.mBigTextChannelId, Constants.mBigTextChannelName, NotificationManager.IMPORTANCE_DEFAULT)
            mManager.createNotificationChannel(channel)
        }
        mBuilder = NotificationCompat.Builder(this, Constants.mBigTextChannelId)
            .setContentTitle("大文本通知")
            .setStyle(NotificationCompat.BigTextStyle().bigText(bigText))
            .setSmallIcon(R.mipmap.ic_launcher)
            .setLargeIcon(BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher))
            .setAutoCancel(true)
        mManager.notify(Constants.mBigTextNotificationId, mBuilder.build())
    }

    /**
     * 大图片通知
     */
    private fun createBigImageNotification() {
        val bigPic = BitmapFactory.decodeResource(resources, R.drawable.ic_launcher_background)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(Constants.mBigImageChannelId, Constants.mBigImageChannelName, NotificationManager.IMPORTANCE_DEFAULT)
            mManager.createNotificationChannel(channel)
        }
        mBuilder = NotificationCompat.Builder(this, Constants.mBigImageChannelId)
            .setContentTitle("大图片通知")
            .setContentText("有美女，展开看看")
            .setStyle(NotificationCompat.BigPictureStyle().bigPicture(bigPic))
            .setSmallIcon(R.mipmap.ic_launcher)
            .setLargeIcon(BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher))
            .setAutoCancel(true)
        mManager.notify(Constants.mBigImageNotificationId, mBuilder.build())
    }

    /**
     * 自定义通知
     */
    private fun createCustomNotification() {
        // 适配8.0及以上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(Constants.mCustomChannelId, Constants.mCustomChannelName, NotificationManager.IMPORTANCE_DEFAULT)
            mManager.createNotificationChannel(channel)
        }

        // 适配12.0及以上
        mFlag = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.FLAG_IMMUTABLE
        } else {
            PendingIntent.FLAG_UPDATE_CURRENT
        }

        // 添加自定义通知view
        val views = RemoteViews(packageName, R.layout.layout_notification)

        // 添加暂停继续事件
        val intentStop = Intent(Constants.mStopAction)
        val pendingIntentStop = PendingIntent.getBroadcast(this, 0, intentStop, mFlag)
        views.setOnClickPendingIntent(R.id.btn_stop, pendingIntentStop)

        // 添加完成事件
        val intentDone = Intent(Constants.mDoneAction)
        val pendingIntentDone = PendingIntent.getBroadcast(this, 0, intentDone, mFlag)
        views.setOnClickPendingIntent(R.id.btn_done, pendingIntentDone)

        // 创建Builder
        mBuilder = NotificationCompat.Builder(this, Constants.mCustomChannelId)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setLargeIcon(BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher))
            .setAutoCancel(true)
            .setCustomContentView(views)
            .setCustomBigContentView(views)// 设置自定义通知view

        // 发起通知
        mManager.notify(Constants.mCustomNotificationId, mBuilder.build())
    }

    /**
     * Description: 更新自定义通知
     */
    private fun updateCustomNotification() {
        sendBroadcast(Intent(Constants.mStopAction))
    }

    /**
     * Description: 更新自定义通知View
     */
    private fun updateCustomView() {
        val views = RemoteViews(packageName, R.layout.layout_notification)
        val intentUpdate = Intent(Constants.mStopAction)
        val pendingIntentUpdate = PendingIntent.getBroadcast(this, 0, intentUpdate, mFlag)
        views.setOnClickPendingIntent(R.id.btn_stop, pendingIntentUpdate)
        // 根据状态更新UI
        if (Constants.mIsStop) {
            views.setTextViewText(R.id.tv_status, "那些你很冒险的梦-停止播放")
            views.setTextViewText(R.id.btn_stop, "继续")
            //mBinding.mbUpdateCustom.text = "继续"
        } else {
            views.setTextViewText(R.id.tv_status, "那些你很冒险的梦-正在播放")
            views.setTextViewText(R.id.btn_stop, "暂停")
            //mBinding.mbUpdateCustom.text = "暂停"
        }

        mBuilder.setCustomContentView(views).setCustomBigContentView(views)
        // 重新发起通知更新UI，注意：必须得是同一个通知id，即mCustomNotificationId
        mManager.notify(Constants.mCustomNotificationId, mBuilder.build())
    }

    /**
     * Description: 创建广播接收器
     */
    private fun createReceiver() {
        val intentFilter = IntentFilter()
        intentFilter.addAction(Constants.mStopAction)
        intentFilter.addAction(Constants.mDoneAction)
        mReceiver = NotificationReceiver()
        registerReceiver(mReceiver, intentFilter)
    }
    private class NotificationReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            // 拦截接收事件
            if (intent.action == Constants.mStopAction) {
                // 改变状态
                Constants.mIsStop = !Constants.mIsStop
                val activity = context as? NotificationActivity
                activity?.updateCustomView()
            } else if (intent.action == Constants.mDoneAction) {
                Toast.makeText(context, "完成", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(mReceiver)
    }

}