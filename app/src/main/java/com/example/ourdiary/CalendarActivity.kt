package com.example.ourdiary

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
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
                var result = ""
                for (document in task.result!!) {
                    val data = document.data
                    result += (data["date"].toString() + " ["
                            + data["feeling"].toString() + ":"
                            + data["title"].toString() + "]\n")
                }
                //binding.dataText?.setText(result) テキストとして出力成功

                binding.recyclerView.layoutManager = LinearLayoutManager(baseContext)
                val adapter = com.example.ourdiary.DiaryAdapter(diary)
                binding.recyclerView.adapter = adapter

            } else {
                //データ取得に失敗した時の処理
            }
        })

    }   //onCreate ↑↑

}