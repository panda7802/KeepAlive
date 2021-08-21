package com.example.keepalive

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
    }

    private fun init() {
    }

    override fun onStart() {
        super.onStart()
        // 启动保活服务
        val startServiceIntent = Intent(this, MyJobService::class.java)
        startService(startServiceIntent)
    }
}