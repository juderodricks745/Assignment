package com.interview.assignment.ui.dash.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.interview.assignment.data.wrappers.BottomWear
import com.interview.assignment.ui.dash.fragments.BottomItemViewFragment
import java.util.*

class BottomWearablesAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    private var bottomWears: List<BottomWear> = ArrayList()

    override fun getItem(position: Int): Fragment {
        return BottomItemViewFragment.newInstance(bottomWears[position])
    }

    override fun getCount(): Int {
        return bottomWears.size
    }

    fun addBottomWearables(bottomWears: List<BottomWear>) {
        this.bottomWears = bottomWears
        notifyDataSetChanged()
    }
}