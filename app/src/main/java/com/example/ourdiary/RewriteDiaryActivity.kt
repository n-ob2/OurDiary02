package com.example.ourdiary

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ourdiary.databinding.ActivityRewriteDiaryBinding
import com.google.gson.Gson

class RewriteDiaryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRewriteDiaryBinding

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
        val diary = Gson().fromJson<Diary>(stateStr, Diary::class.java)
        //変数diaryの中に諸々の日記データが格納されているから、EditText内にセットする。

        binding.rewriteTextDate.setText(diary.date)
        binding.rewriteTextTitle.setText(diary.title)
        binding.rewriteTextSentence.setText(diary.sentence)
        
        /*
        //気分アイコンの中身をセット
        binding.radioGroupFeelings.setOnCheckedChangeListener{ group, checkedId: Int ->
            when (checkedId) {
                R.id.radioButtonHappy ->  diary.feeling = "happy"
                R.id.radioButtonSmile -> diary.feeling = "smile"
                R.id.radioButtonSoso -> diary.feeling = "soso"
                R.id.radioButtonAngry -> diary.feeling = "angry"
                else -> diary.feeling = "sad"
            }
        }
        //天気アイコンの中身をセット
        binding.radioGroupeWeather.setOnCheckedChangeListener{ group, checkedId: Int ->
            when (checkedId) {
                R.id.radioButtonSunny ->  diary.weather = "sunny"
                R.id.radioButtonCloudy -> diary.weather = "cloudy"
                R.id.radioButtonRainy -> diary.weather = "rainy"
                R.id.radioButtonSnow -> diary.weather = "snow"
                else -> diary.weather = "thunder"
            }
        }*/


    }//onCreate ↑↑
}