package org.study2.tablayoutdemo

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class TabPagerAdapter(fa: FragmentActivity, private var tabCount: Int) : FragmentStateAdapter(fa) {

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return Tab1Fragment()
            1 -> return Tab2Fragment()
            2 -> return Tab3Fragment()
            3 -> return Tab4Fragment()

            else -> return Tab1Fragment()
        }
    }

    override fun getItemCount(): Int {
        return tabCount
    }
}