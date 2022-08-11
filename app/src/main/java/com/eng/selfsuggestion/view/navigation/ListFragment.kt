package com.eng.selfsuggestion.view.navigation

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
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

        val adapter = TabFragmentAdapter(requireFragmentManager())
        _binding.viewPager.adapter = adapter
        _binding.tabLayout.setupWithViewPager(_binding.viewPager)

        _binding.tabLayout.post {
            indicatorWidth = _binding.tabLayout.width / _binding.tabLayout.tabCount

            val indicatorParams: FrameLayout.LayoutParams =
                _binding.indicator.layoutParams as FrameLayout.LayoutParams
            indicatorParams.width = indicatorWidth
            _binding.indicator.layoutParams = indicatorParams
        }

        _binding.viewPager.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                val params : FrameLayout.LayoutParams = _binding.indicator.layoutParams as FrameLayout.LayoutParams
                val translationOffset = (position + positionOffset) * indicatorWidth
                params.leftMargin = translationOffset.toInt()
                _binding.indicator.layoutParams = params
            }

            override fun onPageSelected(position: Int) {}
            override fun onPageScrollStateChanged(state: Int) {}

        })


        return _binding.root
    }
}

private class TabFragmentAdapter (
    fragmentManager : FragmentManager
) : FragmentPagerAdapter(fragmentManager) {

    private var arrayList : ArrayList<Fragment> = ArrayList()
    init {
        arrayList.add(DailySpellFragment())
        arrayList.add(SpecialSpellFragment())
    }

    override fun getCount(): Int {
        return arrayList.size
    }

    override fun getItem(position: Int): Fragment {
        return arrayList[position]
    }

}