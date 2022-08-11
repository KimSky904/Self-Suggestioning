package com.eng.selfsuggestion.view.navigation

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.FragmentActivity
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.eng.selfsuggestion.databinding.FragmentListBinding
import com.eng.selfsuggestion.view.spell.DailySpellFragment
import com.eng.selfsuggestion.view.spell.SpecialSpellFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class ListFragment : Fragment() {

    private lateinit var _binding : FragmentListBinding
    private var indicatorWidth : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListBinding.inflate(inflater, container, false)

        val adapter = activity?.let { ViewPagerAdapter(it) }
        _binding.viewPager.adapter = adapter
        _binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {}
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {
                _binding.viewPager.currentItem = tab!!.position
            }
        })

        return _binding.root
    }
}

private class ViewPagerAdapter(
    fragment : FragmentActivity
) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> DailySpellFragment()
            1 -> SpecialSpellFragment()
            else -> DailySpellFragment()
        }
    }

}