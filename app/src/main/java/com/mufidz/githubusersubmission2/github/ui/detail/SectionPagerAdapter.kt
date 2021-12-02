package com.mufidz.githubusersubmission2.github.ui.detail

import android.content.Context
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.mufidz.githubusersubmission2.R
import com.mufidz.githubusersubmission2.github.ui.fragment.FollowersFragment
import com.mufidz.githubusersubmission2.github.ui.fragment.FollowingFragment

class SectionPagerAdapter(private val mCtx: Context, fm: FragmentManager, data: Bundle) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private var fragmntBundle: Bundle

    init {
        fragmntBundle = data
    }

    @StringRes
    private val TAB_TITLES = intArrayOf(R.string.tab1, R.string.tab2)

    override fun getCount(): Int = 2

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FollowersFragment()
            1 -> fragment = FollowingFragment()
        }
        fragment?.arguments = this.fragmntBundle
        return fragment as Fragment
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mCtx.resources.getString(TAB_TITLES[position])
    }

}