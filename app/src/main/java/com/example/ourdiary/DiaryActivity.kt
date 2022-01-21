package com.example.ourdiary

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ourdiary.databinding.ActivityDiaryBinding
import com.google.gson.Gson

class DiaryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDiaryBinding

    companion object {
        val KEY_STATE = "key_state"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ///setContentView(R.layout.activity_diary)

        binding =  ActivityDiaryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val stateStr = intent.getStringExtra(KEY_STATE)
        // Gsonで String → Object
        val diary = Gson().fromJson<Diary>(stateStr, Diary::class.java)
        //変数diaryの中に諸々の日記データが格納されているから、bindingで各ViewにsetTextすればよい。

        binding.textViewDate.setText(diary.date)
        //天気アイコンの表示
        when (diary.weather){
           "sunny" -> binding.imageViewWeather.setImageResource(R.drawable.sunny)
           "cloudy" -> binding.imageViewWeather.setImageResource(R.drawable.cloudy)
           "rainy" -> binding.imageViewWeather.setImageResource(R.drawable.rainy)
           "snow" -> binding.imageViewWeather.setImageResource(R.drawable.snow)
           else -> binding.imageViewWeather.setImageResource(R.drawable.thunder)
        }
        //気分アイコンの表示
        when (diary.feeling){
            "happy" -> binding.imageViewFeeling.setImageResource(R.drawable.happy)
            "smile" -> binding.imageViewFeeling.setImageResource(R.drawable.smile)
            "soso" -> binding.imageViewFeeling.setImageResource(R.drawable.soso)
            "angry" -> binding.imageViewFeeling.setImageResource(R.drawable.angry)
            else -> binding.imageViewFeeling.setImageResource(R.drawable.sad)
        }

        binding.textViewTitle.setText(diary.title)
        binding.textViewSentence.setText(diary.sentence)
    }
}