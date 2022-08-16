package com.eng.selfsuggestion.view.navigation

import android.animation.ObjectAnimator
import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
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
import com.eng.selfsuggestion.view.spell.*
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class ListFragment : Fragment() {

    private lateinit var _binding : FragmentListBinding
    private var isDaily : Boolean = true
    private var isChecked : Boolean = false
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
        _binding.viewPager.bringToFront()
        _binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                var positionValue = tab!!.position
                when(positionValue) {
                    0 -> Log.e("TAG", "Daily Selected")
                    1 -> Log.e("TAG", "Special Selected")
                }
                moveIndicator(positionValue)
                _binding.viewPager.postDelayed({
                    _binding.viewPager.currentItem = positionValue
                }, 550)
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

        TabLayoutMediator(_binding.tabLayout, _binding.viewPager) {
            _, position -> moveIndicator(position)
            Log.e("TAG", "Something Slided")
        }.attach()

        _binding.txtIndicatorSpecial.setOnClickListener {
            Log.e("TAG", "Special Clicked")
            moveIndicator(1)
        }
        _binding.txtIndicatorDaily.setOnClickListener {
            Log.e("TAG", "Daily Clicked")
            moveIndicator(0)
        }

        _binding.btnFloatingAction.setOnClickListener {
            onAddButtonClicked()
        }
        _binding.btnToOthers.setOnClickListener {
            val intent = Intent(activity, AddToOthersSpellActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
        }
        _binding.btnDailySpell.setOnClickListener {
            val intent = Intent(activity, AddDailySpellActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
        }
        _binding.btnSpecialSpell.setOnClickListener {
            val intent = Intent(activity, AddSpecialSpellActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
            activity?.overridePendingTransition(R.anim.translate_none,R.anim.translate_none)
        }


        return _binding.root
    }

    private fun moveIndicator(position: Int) {
        when(position) {
            0 -> {
                if(!isDaily) {
                    ObjectAnimator.ofFloat(_binding.movingIndicator, "translationX", 0f).apply {
                        duration = 600
                        _binding.viewPager.currentItem = 0
                        start()
                    }
                    _binding.txtIndicatorDaily.postDelayed({
                        _binding.txtIndicatorDaily.setTextColor(Color.WHITE)
                        _binding.txtIndicatorSpecial.setTextColor(Color.BLACK)
                    }, 350)
                    isDaily = !isDaily
                }
            }
            1 -> {
                if(isDaily) {
                    ObjectAnimator.ofFloat(_binding.movingIndicator, "translationX", 420f).apply {
                        duration = 600
                        _binding.viewPager.currentItem = 1
                        start()
                    }
                    _binding.txtIndicatorSpecial.postDelayed({
                        _binding.txtIndicatorDaily.setTextColor(Color.BLACK)
                        _binding.txtIndicatorSpecial.setTextColor(Color.WHITE)
                    }, 350)

                    isDaily = !isDaily
                }
            }
        }
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