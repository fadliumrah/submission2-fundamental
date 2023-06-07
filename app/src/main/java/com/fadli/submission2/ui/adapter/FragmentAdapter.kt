package com.fadli.submission2.ui.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.fadli.submission2.ui.follower.FollowerFragment
import com.fadli.submission2.ui.following.FollowingFragment
import com.fadli.submission2.util.Constanta.TAB_TITLES

class FragmentAdapter (activity: AppCompatActivity, private val username: String) : FragmentStateAdapter(activity){
    override fun getItemCount(): Int {
        return TAB_TITLES.size
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when(position){
            0 -> fragment = FollowerFragment.newInstance(username)
            1 -> fragment = FollowingFragment.newInstance(username)
        }
        return fragment as Fragment
    }
}