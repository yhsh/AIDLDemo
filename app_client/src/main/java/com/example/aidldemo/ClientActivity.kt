package com.example.aidldemo

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.os.RemoteException
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class ClientActivity : AppCompatActivity() {
    private val TAG = "ClientActivity"
    private var mConnected = false
    private var mITokenAidlInterface: ITokenAidlInterface? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client)
        val btSend = findViewById<Button>(R.id.bt_send)
        val btSend2 = findViewById<Button>(R.id.bt_send2)
        val etInput = findViewById<EditText>(R.id.et_input)
        //跨进程方法一 AIDL
        btSend.setOnClickListener {
            try {
                //AIDL跨进程发送数据到服务端APP
                mITokenAidlInterface!!.postLoginToken(etInput.text.toString().trim())
            } catch (e: RemoteException) {
                e.printStackTrace()
            }
        }
        //跨进程方法二 广播
        btSend2.setOnClickListener {
            val intent = Intent()
            //下面action需要与服务端APP清单文件里面的广播配置的action字段保持一致
            intent.action = "com.xiayiye5.login.token2"
            intent.putExtra("login_access_token", "拿到的token为：" + etInput.text.toString().trim())
            sendBroadcast(intent)
        }
    }

    /**
     * 尝试与服务端建立连接
     */
    private fun startBindService() {
        val intent = Intent()
        //下面action需要与服务端APP清单文件里面的服务配置的action字段保持一致
        intent.action = "com.xiayiye5.login.token"
        //要发送到服务端APP的包名
        intent.setPackage("com.example.app_server")
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE)
    }

    private val mServiceConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            Log.d(TAG, "service connected")
            mITokenAidlInterface = ITokenAidlInterface.Stub.asInterface(service)
            //如果与服务端的连接处于未连接状态，则尝试连接
            if (!mConnected) {
                startBindService()
                Toast.makeText(
                    this@ClientActivity,
                    "当前与服务端处于未连接状态，正在尝试重连，请稍后再试",
                    Toast.LENGTH_SHORT
                ).show()
                return
            }
        }

        override fun onServiceDisconnected(name: ComponentName) {
            Log.d(TAG, "service disconnected")
            mConnected = false
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart")
        if (!mConnected) {
            //建立连接
            startBindService()
        }
    }

    override fun onStop() {
        super.onStop()
        if (mConnected) {
            //解绑服务
            unbindService(mServiceConnection)
        }
    }

}