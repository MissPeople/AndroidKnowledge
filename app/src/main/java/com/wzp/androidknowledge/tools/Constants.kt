package com.wzp.androidknowledge.tools

object Constants {

    //--------------------Notification start-------------------
    const val mNormalChannelId = "渠道id" // 唯一性
    const val mNormalChannelName = "渠道名称"
    const val mHighChannelId = "重要渠道id"
    const val mHighChannelName = "重要通知"
    const val mProgressChannelId = "进度条渠道id"
    const val mProgressChannelName = "进度条通知"
    const val mBigTextChannelId = "大文本渠道id"
    const val mBigTextChannelName = "大文本通知"
    const val mBigImageChannelId = "大图片渠道id"
    const val mBigImageChannelName = "大图片通知"
    const val mCustomChannelId = "自定义渠道id"
    const val mCustomChannelName = "自定义通知"

    const val mNormalNotificationId = 9001 // 通知id
    const val mHighNotificationId = 9002 // 通知id
    const val mBigTextNotificationId = 9003 // 通知id
    const val mProgressNotificationId = 9004 // 通知id
    const val mBigImageNotificationId = 9005 // 通知id
    const val mCustomNotificationId = 9006 // 通知id
    const val mStopAction = "com.yechaoa.stop" // 暂停继续action
    const val mDoneAction = "com.yechaoa.done" // 完成action
    var mIsStop = false // 是否在播放 默认未开始
    //--------------------Notification end-------------------
}