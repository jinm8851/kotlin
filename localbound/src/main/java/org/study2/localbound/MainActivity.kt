package org.study2.localbound

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import org.study2.localbound.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    var myService:BoundService? = null
    var isBound = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val intent = Intent(this,BoundService::class.java)
        bindService(intent,myConnection, Context.BIND_AUTO_CREATE)

        binding.button.setOnClickListener {
            val currentTime = myService?.getCurrentTime()
            binding.myTextView.text = currentTime
        }
    }
//   ServiceConnection 의 서브클래스생성과 onServiceConnected 와 onServiceDisconnected 구현
    private val myConnection = object : ServiceConnection {
        override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
            val binder = p1 as BoundService.MyLocalBinder
            myService = binder.getService()
            isBound = true
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
            isBound = false
        }
    }
}