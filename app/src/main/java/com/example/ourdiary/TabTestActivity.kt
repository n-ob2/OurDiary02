package com.example.ourdiary

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ourdiary.databinding.ActivityTabTestBinding

class TabTestActivity : AppCompatActivity() {
    private lateinit var binding: TabTestActivity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tab_test)

        binding = TabTestActivity.inflate(layoutInflater)
        setContentView(binding.root)

        pager.adapter = TabAdapter( supportFragmentManager, this)
        tab_layout.setupWithViewPager(pager)

    }
}