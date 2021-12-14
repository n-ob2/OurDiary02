package com.example.ourdiary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.ourdiary.databinding.FragmentCalendarBinding
import java.util.*



class CalenderFragment : Fragment() {

    private var _binding: FragmentCalendarBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCalendarBinding.inflate(inflater, container, false)
        return binding.root
    }   //onCreateView↑↑

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //(activity as? CalendarActivity)?.setFabVisible(View.VISIBLE)
        var dateTime = Calendar.getInstance().apply {
            timeInMillis = binding.calendarView.date
        }

        binding.calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            changeDate(year, month, dayOfMonth)
        }
    }   //onViewCreated↑↑

    private fun changeDate(year: Int, month: Int, dayOfMonth: Int) {
        var selectDate = Calendar.getInstance().apply {
            clear()
            set(year, month, dayOfMonth)
        }

        fun onDestroyView() {
            super.onDestroyView()
            _binding = null
        }

        fun onDestroy() {
            super.onDestroy()
            //return.close()
        }
    }
}
