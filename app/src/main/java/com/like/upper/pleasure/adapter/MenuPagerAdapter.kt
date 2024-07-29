package com.like.upper.pleasure.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.like.upper.pleasure.fm.HomeFragment
import com.like.upper.pleasure.fm.MineFragment
import com.like.upper.pleasure.fm.OrderFragment

class MenuPagerAdapter(var context: AppCompatActivity, var size : Int) : FragmentStateAdapter(context) {

    val list = mutableListOf<Fragment>()

    override fun getItemCount() = size

    override fun createFragment(position: Int): Fragment {
        when(position){
            0 -> {
                return HomeFragment()
            }
            1 ->{
                return OrderFragment()
            }
            2 ->{
                return MineFragment()
            }
        }
        return HomeFragment()
    }


}