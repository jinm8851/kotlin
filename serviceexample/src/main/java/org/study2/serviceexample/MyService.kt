package org.study2.serviceexample

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.Exception

class MyService : Service() {
    private val TAG = "ServiceExample"
    private val coroutineScope = CoroutineScope(Dispatchers.Default)

    override fun onCreate() {
        Log.i(TAG,"Service onCreate")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        //코루틴으로 메인쓰레드 밖에서 다른쓰레드로 돌려야한다
        
        


        coroutineScope.launch(Dispatchers.Default){
            performTask(startId)
        }
        return Service.START_STICKY
    }

    suspend fun performTask(startId: Int){

        Log.i(TAG,"Service onStartCommand  "+ startId)
        var i=0
        while (i<=3 ){
            try {
                delay(10000)
                i++
            }catch (e:Exception){}
            Log.i(TAG,"Service running" + startId)
        }

    }

    override fun onBind(intent: Intent): IBinder {
        Log.i(TAG,"Service onBind")
        TODO("Return the communication channel to the service.")
    }

    override fun onDestroy() {
        Log.i(TAG,"Service onDestroy")
    }
}
