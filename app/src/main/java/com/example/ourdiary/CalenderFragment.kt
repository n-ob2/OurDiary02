package com.example.ourdiary

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.ourdiary.databinding.FragmentCalenderBinding
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*


class CalenderFragment : Fragment() {
    private lateinit var userId: String

    private var _binding: FragmentCalenderBinding? = null
    private val binding get() = _binding!!

    private var db: FirebaseFirestore? = null
    private var diary: CollectionReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //ユーザーID取得
        val data = getSharedPreferences("UserIdDataStore", Context.MODE_PRIVATE)
        userId = data.getString("UserId", "").toString()

        //FirebaseApp.initializeApp(this)
        val db = Firebase.firestore
        diary = db.collection("users")

        diary!!.addSnapshotListener { snapshot, e ->
            var result = ""
            val items: Iterator<QueryDocumentSnapshot> = snapshot!!.iterator()
            while (items.hasNext()) {
                val docdata = items.next()
                val data = docdata.data
                result += """${data["date"]}"""
            }
            //データベースの日付を取得してやりたいこと
            //カレンダーでタップされた日付をインテントで渡して、該当の日記の内容を表示？
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCalenderBinding.inflate(inflater, container, false)
        return binding.root
    }   //onCreateView↑↑


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //(activity as? CalendarActivity)?.setFabVisible(View.VISIBLE)
        var dateTime = Calendar.getInstance().apply {
            timeInMillis = binding.calendarView.date
        }

        findDiary(
            dateTime.get(Calendar.YEAR),
            dateTime.get(Calendar.MONTH),
            dateTime.get(Calendar.DAY_OF_MONTH)
        )

        binding.calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            changeDate(year, month, dayOfMonth)
            findDiary(year, month, dayOfMonth)
        }
    }   //onViewCreated↑↑

    private fun findDiary(year: Int, month: Int, dayOfMonth: Int) {
        //カレンダーでタップされた日付をインテントで渡して、データベースで該当の日記の内容を表示？
    }


    private fun changeDate(year: Int, month: Int, dayOfMonth: Int) {
        var selectDate = Calendar.getInstance().apply {
            clear()
            set(year, month, dayOfMonth)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        //return.close()
    }

}
