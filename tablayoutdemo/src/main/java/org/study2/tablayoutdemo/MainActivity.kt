package org.study2.tablayoutdemo

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.tabs.TabLayoutMediator
import org.study2.tablayoutdemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)


        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        configureTabLayout()
    }

    private fun configureTabLayout() {
        //네개의 탭을 생성하면서 각탭의 텍스트를 지정한다
        repeat(4) {
            binding.tabLayout.addTab(binding.tabLayout.newTab())
        }

        //TabPagerAdapter글래스의 인스턴스를 생성한다 이때 TabLayout컴포넌트에 지정된 탭의 개수를 TabPagerAdapter의 생정자 인자로 전달한다
        val adapter = TabPagerAdapter(this, binding.tabLayout.tabCount)
        //TabPagerAdapter인스턴스를 viewPater2 인스턴스의 어댑터로 지정한다
        binding.viewPager.adapter = adapter

        // TabLayoutMediator 클래스의 인스턴스를 사용하서 TabLayout을 viewPater2 인스턴스와 연결한다
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = "Tab ${(position + 1)} Item"
        }.attach()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }


}