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

        binding =  ActivityDiaryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val stateStr = intent.getStringExtra(KEY_STATE)
        // Gsonで String → Object
        val diary = Gson().fromJson<Diary>(stateStr, Diary::class.java)
        //変数diaryの中に諸々の日記データが格納されているから、bindingで各ViewにsetTextすればよい。

        binding.textViewDate.setText(diary.date)

       when (diary.weather){
           "sunny" -> binding.imageViewWeather.setImageResource(R.drawable.sunny)
           "cloudy" -> binding.imageViewWeather.setImageResource(R.drawable.cloudy)
           "rainy" -> binding.imageViewWeather.setImageResource(R.drawable.rainy)
           "snow" -> binding.imageViewWeather.setImageResource(R.drawable.snow)
           else -> binding.imageViewWeather.setImageResource(R.drawable.thunder)
       }

        binding.textViewTitle.setText(diary.title)
        binding.textViewSentence.setText(diary.sentence)
    }
}