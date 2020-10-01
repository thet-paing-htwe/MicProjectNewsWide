package com.tphtwe.newswide.ui.detail

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

@Suppress("DEPRECATION")
class PagerAdapter(private val mContext: Context, fm: FragmentManager, private var totalTabs: Int) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> {
                //  val homeFragment: HomeFragment = HomeFragment()
                return TodayCountryFragment()
            }
            1 -> {
                return YesterdayCountryFragment()
            }
            else -> return getItem(position)
        }

    }

    override fun getCount(): Int {
        return totalTabs
    }

}