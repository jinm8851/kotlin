package org.study2.mywebview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.study2.mywebview.databinding.ActivityMainBinding
import java.lang.Exception
import java.net.URL

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        handleIntent()
    }


    private fun handleIntent() {
        val intent = this.intent
        val data = intent.data
        var url: URL? = null
        try {
            url = URL(data?.scheme, data?.host, data?.path)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        binding.webWiew1.loadUrl(url?.toString() ?: "https://ww.amazon.com")
    }
}
