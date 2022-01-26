package org.study2.coroutinedemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import kotlinx.coroutines.*
import org.study2.coroutinedemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private var count: Int = 1
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                count = p1
                binding.countText.text = "${count} coroutines"
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }
        })
    }

    suspend fun performTask(tasknumber: Int):Deferred<String> =
        coroutineScope.async(Dispatchers.Main){
            delay(5000)
            return@async "Finished Coroutine ${tasknumber}"
        }

    fun launchCoroutines(view:View) {
        (1..count).forEach{
            binding.statusText.text = "Started Coroutine ${it}"
            coroutineScope.launch(Dispatchers.Main){
                binding.statusText.text = performTask(it).await()
            }
        }
    }
}