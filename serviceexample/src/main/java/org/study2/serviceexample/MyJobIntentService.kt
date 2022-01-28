package org.study2.serviceexample

import android.content.Intent
import android.util.Log
import androidx.core.app.JobIntentService
import java.lang.Exception

class MyJobIntentService : JobIntentService() {

    private val TAG = "ServiceExample"

    override fun onHandleWork(intent: Intent) {
        Log.i(TAG,"Job Service started")

        var i = 0

        while (i<=3 ) {
            try {
                Thread.sleep(10000)
                i++
            }catch (e:Exception){}
            Log.i(TAG,"Service running")
        }
    }
}