package com.example.app_server

import android.os.Bundle
import android.text.TextUtils
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ServerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_server)
        val tvShowMessage = findViewById<TextView>(R.id.tv_show_message)
        val accessToken = intent.getStringExtra("accessToken")
        val loginAccessToken = intent.getStringExtra("login_access_token")
        if (!TextUtils.isEmpty(accessToken)) {
            tvShowMessage.text = "拿到的token值为：$accessToken"
            Toast.makeText(this, "拿到的token为：$accessToken", Toast.LENGTH_SHORT).show()
        } else if (!TextUtils.isEmpty(loginAccessToken)) {
            tvShowMessage.text = "拿到的token2值为：$loginAccessToken"
            Toast.makeText(this, "拿到的token2为：$loginAccessToken", Toast.LENGTH_SHORT).show()
        }
    }
}