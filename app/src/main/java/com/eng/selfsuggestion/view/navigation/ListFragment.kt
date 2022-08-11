package com.eng.selfsuggestion.view.navigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.eng.selfsuggestion.R
import com.eng.selfsuggestion.databinding.FragmentListBinding
import com.eng.selfsuggestion.view.spell.DailySpellFragment
import com.eng.selfsuggestion.view.spell.SpecialSpellFragment
import com.google.android.material.tabs.TabLayout

class ListFragment : Fragment() {

    private lateinit var _binding : FragmentListBinding
    // private var indicatorWidth : Int = 0
    private var isClicked : Boolean = false
    private val rotateOpen : Animation by lazy { AnimationUtils.loadAnimation(context, R.anim.rotate_open_anim) }
    private val rotateClose : Animation by lazy { AnimationUtils.loadAnimation(context, R.anim.rotate_close_anim) }
    private val fromBottom : Animation by lazy { AnimationUtils.loadAnimation(context, R.anim.from_bottom_anim) }
    private val toBottom : Animation by lazy { AnimationUtils.loadAnimation(context, R.anim.to_bottom_anim) }


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

        _binding.btnFloatingAction.setOnClickListener {
            onAddButtonClicked()
        }
        _binding.btnToOthers.setOnClickListener {

        }
        _binding.btnDailySpell.setOnClickListener {

        }
        _binding.btnSpecialSpell.setOnClickListener {

        }


        return _binding.root
    }

    private fun onAddButtonClicked() {
        setVisibility(isClicked)
        setAnimation(isClicked)
        isClicked = !isClicked
    }
    private fun setVisibility(isClicked : Boolean) {
        if(!isClicked) {
            _binding.btnToOthers.visibility = View.VISIBLE
            _binding.btnDailySpell.visibility = View.VISIBLE
            _binding.btnSpecialSpell.visibility = View.VISIBLE

            _binding.txtToOthers.visibility = View.VISIBLE
            _binding.txtDailySpell.visibility = View.VISIBLE
            _binding.txtSpecialSpell.visibility = View.VISIBLE
        } else {
            _binding.btnToOthers.visibility = View.INVISIBLE
            _binding.btnDailySpell.visibility = View.INVISIBLE
            _binding.btnSpecialSpell.visibility = View.INVISIBLE

            _binding.txtToOthers.visibility = View.INVISIBLE
            _binding.txtDailySpell.visibility = View.INVISIBLE
            _binding.txtSpecialSpell.visibility = View.INVISIBLE
        }
    }
    private fun setAnimation(isClicked: Boolean) {
        if(!isClicked) {
            _binding.btnToOthers.startAnimation(fromBottom)
            _binding.btnDailySpell.startAnimation(fromBottom)
            _binding.btnSpecialSpell.startAnimation(fromBottom)
            _binding.txtToOthers.startAnimation(fromBottom)
            _binding.txtDailySpell.startAnimation(fromBottom)
            _binding.txtSpecialSpell.startAnimation(fromBottom)
            _binding.btnFloatingAction.startAnimation(rotateOpen)
        } else {
            _binding.btnToOthers.startAnimation(toBottom)
            _binding.btnDailySpell.startAnimation(toBottom)
            _binding.btnSpecialSpell.startAnimation(toBottom)
            _binding.txtToOthers.startAnimation(toBottom)
            _binding.txtDailySpell.startAnimation(toBottom)
            _binding.txtSpecialSpell.startAnimation(toBottom)
            _binding.btnFloatingAction.startAnimation(rotateClose)
        }
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