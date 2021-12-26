package com.example.ourdiary

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class TabAdapter(fm:FragmentManager, private val context: Context): FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        when(position){
            0 -> { return EditDiaryFragment() }
            else -> { return GachaDiaryFragment() }
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when (position){
            0 -> { return "じぶんで決める"}
            else -> {return "ガチャでえらぶ"}
        }
    }

    override fun getCount(): Int {
        return 2
    }
}