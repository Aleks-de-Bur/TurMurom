package com.example.turmurom.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class VpAdapter(fragAdapter: FragmentActivity,
    private val list: List<Fragment>) : FragmentStateAdapter(fragAdapter) {
    override fun getItemCount(): Int {
     return list.size
    }

    override fun createFragment(position: Int): Fragment {
        return list[position]
    }
}