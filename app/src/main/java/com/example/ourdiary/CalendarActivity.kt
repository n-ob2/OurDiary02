package com.example.ourdiary

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ourdiary.databinding.ActivityCalendarBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot


class CalendarActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCalendarBinding

    private var db: FirebaseFirestore? = null
    private var diary: CollectionReference? = null
    private var dataText: EditText? = null

    lateinit var mAdapter: DiaryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)

        binding = ActivityCalendarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.floatingActionButton.setOnClickListener {
            val intent = Intent(this, EditDiaryActivity::class.java)
            startActivity(intent)
        }

        db = FirebaseFirestore.getInstance()
        diary = db!!.collection("diary")


        diary!!.get().addOnCompleteListener(OnCompleteListener<QuerySnapshot?> { task ->
            if (task.isSuccessful) {
                var result = mutableListOf<Diary>()
                for (document in task.result!!) {
                    var diary2 = Diary()
                    val data = document.data
                    diary2.date = data["date"].toString()
                    diary2.feeling = data["feeling"].toString()
                    diary2.title = data["title"].toString()
                    result.add(diary2)
                }

                //binding.dataText?.setText(result) //テキストとして出力成功

                val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
                recyclerView.layoutManager = LinearLayoutManager(this)
                mAdapter  = DiaryAdapter(result)
                recyclerView.adapter = mAdapter
            } else {
                //データ取得に失敗した時の処理
            }
        })

    }   //onCreate ↑↑

    private fun createDatalist() {
        val dataList = mutableListOf<Diary>()
    }

}