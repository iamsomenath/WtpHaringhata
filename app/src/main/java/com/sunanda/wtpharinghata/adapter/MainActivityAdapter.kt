package com.sunanda.wtpharinghata.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.sunanda.wtpharinghata.view.BlankFragment
import com.sunanda.wtpharinghata.view.ClearWaterFragment
import com.sunanda.wtpharinghata.view.RawWaterFragment
import com.sunanda.wtpharinghata.view.TreatedWaterFragment

class MainActivityAdapter(private val myContext: Context, fm: FragmentManager, internal var totalTabs: Int)
    : FragmentPagerAdapter(fm) {

    // this is for fragment tabs
    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> {
                return RawWaterFragment()
            }
            1 -> {
                return ClearWaterFragment()
            }
            2 -> {
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