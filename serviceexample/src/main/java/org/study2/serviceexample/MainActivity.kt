package org.study2.serviceexample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.app.JobIntentService.enqueueWork

class MainActivity : AppCompatActivity() {

    val SERVICE_ID = 1001


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



    }



    fun buttonClick(view:View){
        intent = Intent(this,MyService::class.java)
        startService(intent)
    }
}