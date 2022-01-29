package com.example.ourdiary

//import android.content.Intent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ourdiary.databinding.ActivityCalendarBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.gson.Gson


class CalendarActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCalendarBinding

    private var db: FirebaseFirestore? = null
    private var diary: CollectionReference? = null
    private var dataText: EditText? = null

    private lateinit var userId: String

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

        //ユーザーID取得
        val data = getSharedPreferences("UserIdDataStore", Context.MODE_PRIVATE)
        userId = data.getString("UserId", "").toString()

        db = FirebaseFirestore.getInstance()
        diary = db!!.collection("UserList").document(userId!!).collection("diary")

        diary!!.get().addOnCompleteListener(OnCompleteListener<QuerySnapshot?> { task ->
            if (task.isSuccessful) {
                var result = mutableListOf<Diary>()
                for (document in task.result!!) {
                    var diary2 = Diary()
                    val data = document.data
                    diary2.id = document.id
                    diary2.date = data["date"].toString()
                    diary2.feeling = data["feeling"].toString()
                    diary2.weather = data["weather"].toString()
                    diary2.title = data["title"].toString()
                    diary2.sentence = data["sentence"].toString()
                    result.add(diary2)
                }

                //binding.dataText?.setText(result) //テキストとして出力成功

                val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
                recyclerView.layoutManager = LinearLayoutManager(this)
                mAdapter  = DiaryAdapter(result)
                recyclerView.adapter = mAdapter

                mAdapter.setOnDiaryCellClickListener(object : DiaryAdapter.OnDiaryCellClickListener{
                    override fun onItemClick(diary: Diary) {
                        // 書籍データを渡す処理(クリックしたときに何をしたいか)
                        // 次のアクティビティに"diary"の中身のデータを渡す処理。※独自のclassなので簡単に渡せない
                        val intent = Intent(this@CalendarActivity, DiaryActivity::class.java)
                        // Gsonで Object → String
                        intent.putExtra(DiaryActivity.KEY_STATE, Gson().toJson(diary))
                        startActivity(intent)

                        }})
            } else {
                //データ取得に失敗した時の処理
            }
        })

    }   //onCreate ↑↑

    private fun createDatalist() {
        val dataList = mutableListOf<Diary>()
    }

    fun gotoSetting(view: View?){
        val intent = Intent(this, SettingActivity::class.java)
        startActivity(intent)
    }

}