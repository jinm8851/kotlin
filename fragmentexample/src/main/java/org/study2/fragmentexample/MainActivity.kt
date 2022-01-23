package org.study2.fragmentexample


import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import org.study2.fragmentexample.databinding.ActivityMainBinding

class MainActivity : FragmentActivity(),ToolbarFragment.ToolbarListener {
    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

    }

    override fun onButtonClick(fontsize: Int, text: String) {
        val textFragment = supportFragmentManager.findFragmentById(R.id.text_fragment) as TextFragment
        textFragment.changeTextProperties(fontsize,text)
    }
}