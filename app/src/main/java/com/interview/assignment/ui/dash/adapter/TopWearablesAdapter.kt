package com.interview.assignment.ui.dash.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.interview.assignment.data.wrappers.TopWear
import com.interview.assignment.ui.dash.fragments.TopItemViewFragment
import java.util.*

class TopWearablesAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    private var topWears: List<TopWear> = ArrayList()

    override fun getItem(position: Int): Fragment {
        return TopItemViewFragment.newInstance(topWears[position])
    }

    override fun getCount(): Int {
        return topWears.size
    }

    fun addTopWearables(topWears: List<TopWear>) {
        this.topWears = topWears
        notifyDataSetChanged()
    }
}