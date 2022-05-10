package com.example.newsapp.ui.Adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.newsapp.databinding.FragmentEntertainmentBinding
import com.example.newsapp.ui.view.*

class PagerAdapter (manager: FragmentManager, lifecycle: Lifecycle): FragmentStateAdapter(manager, lifecycle){
    override fun getItemCount(): Int = 6

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> HomeFragment()
            1 -> ScienceFragment()
            2 -> SportFragment()
            3 -> EntertainmentFragment()
            4 -> BusinessFragment()
            5 -> HealthFragment()

            else -> {HealthFragment()}
        }
    }



}