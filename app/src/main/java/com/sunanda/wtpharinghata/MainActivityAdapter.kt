package com.sunanda.wtpharinghata

import android.app.Activity
import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class MainActivityAdapter(private val myContext: Context, fm: FragmentManager, internal var totalTabs: Int)
    : FragmentPagerAdapter(fm) {

    // this is for fragment tabs
    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> {
                return RawWaterFragment()
            }
            1 -> {
                return TreatedWaterFragment()
            }
            else -> return BlankFragment()
        }
    }

    // this counts total number of tabs
    override fun getCount(): Int {
        return totalTabs
    }
}