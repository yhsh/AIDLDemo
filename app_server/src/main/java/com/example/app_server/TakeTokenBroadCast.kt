package com.example.app_server

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

/**
 * 广播接收跨进程APP数据
 * @author xiayiye5
 * @date 2021/11/29 9:43
 */
class TakeTokenBroadCast : BroadcastReceiver() {
    companion object {
        const val action = "com.xiayiye5.login.token2"
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        if (action == intent!!.action) {
//            val token = intent.getStringExtra("login_access_token")
//            Toast.makeText(context, token, Toast.LENGTH_LONG).show()
            intent.setClass(context!!, ServerActivity::class.java)
            context.startActivity(intent)
        }
    }
}