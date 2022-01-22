package com.example.ourdiary

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ourdiary.databinding.ActivityRewriteDiaryBinding
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson


class RewriteDiaryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRewriteDiaryBinding
    private lateinit var diary: Diary

    private var dateText: EditText? = null
    private var feelingText: String? = null
    private var weatherText: String? = null
    private var titleText: EditText? = null
    private var sentenceText: EditText? = null
    private var dataText: EditText? = null

    private var db: FirebaseFirestore? = null
    private var diaryDb: CollectionReference? = null
    private var get_id: String? = null


    companion object {
        val KEY_STATE = "key_state"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rewrite_diary)

        binding = ActivityRewriteDiaryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val stateStr = intent.getStringExtra(DiaryActivity.KEY_STATE)
        // Gsonで String → Object
        diary = Gson().fromJson<Diary>(stateStr, Diary::class.java)
        //変数diaryの中に諸々の日記データが格納されているから、EditText内にセットする。

        binding.rewriteTextDate.setText(diary.date)
        binding.rewriteTextTitle.setText(diary.title)
        binding.rewriteTextSentence.setText(diary.sentence)


        //FirebaseApp.initializeApp(this)
        val db = Firebase.firestore
        diaryDb = db.collection("diary")


        //各気分のラジオボタンが選ばれた時に変数feelingTextに値を代入
        binding.radioGroupFeelings.setOnCheckedChangeListener { group, checkedId: Int ->
            when (checkedId) {
                R.id.radioButtonHappy -> feelingText = "happy"
                R.id.radioButtonSmile -> feelingText = "smile"
                R.id.radioButtonSoso -> feelingText = "soso"
                R.id.radioButtonAngry -> feelingText = "angry"
                else -> feelingText = "sad"
            }
        }
        //各天気のラジオボタンが選ばれた時に変数weatherTextに値を代入
        binding.radioGroupeWeather.setOnCheckedChangeListener { group, checkedId: Int ->
            when (checkedId) {
                R.id.radioButtonSunny -> weatherText = "sunny"
                R.id.radioButtonCloudy -> weatherText = "cloudy"
                R.id.radioButtonRainy -> weatherText = "rainy"
                R.id.radioButtonSnow -> weatherText = "snow"
                else -> weatherText = "thunder"
            }
        }

    }//onCreate ↑↑



    fun doRewrite(view: View) {
        diaryDb!!.document(diary.id).get().addOnCompleteListener{}

        val date: String = binding.rewriteTextDate!!.getText().toString()
        val feel: String = feelingText.toString()
        val weather: String = weatherText.toString()
        val tit: String = binding.rewriteTextTitle!!.getText().toString()
        val sen: String = binding.rewriteTextSentence!!.getText().toString()
        val data: MutableMap<String, Any> = HashMap()
        data["date"] = date
        data["feeling"] = feel
        data["weather"] = weather
        data["title"] = tit
        data["sentence"] = sen

        diaryDb!!.document(diary.id).set(data)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        this, "日記を更新（こうしん）しました!",
                        Toast.LENGTH_LONG
                    ).show()
                    val intent = Intent(this, CalendarActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(
                        this, "更新（こうしん）できませんでした。",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
}

