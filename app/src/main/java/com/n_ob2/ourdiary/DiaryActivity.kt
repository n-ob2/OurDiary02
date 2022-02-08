package com.n_ob2.ourdiary

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.n_ob2.ourdiary.databinding.ActivityDiaryBinding
import com.google.gson.Gson


class DiaryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDiaryBinding
    private lateinit var diary: Diary

    companion object {
        val KEY_STATE = "key_state"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diary)

        binding = ActivityDiaryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val stateStr = intent.getStringExtra(KEY_STATE)
        // Gsonで String → Object
        diary = Gson().fromJson<Diary>(stateStr, Diary::class.java)
        //変数diaryの中に諸々の日記データが格納されているから、bindingで各ViewにsetTextすればよい。

        binding.textViewDate.setText(diary.date)
        //天気アイコンの表示
        when (diary.weather) {
            "sunny" -> binding.imageViewWeather.setImageResource(R.drawable.sunny)
            "cloudy" -> binding.imageViewWeather.setImageResource(R.drawable.cloudy)
            "rainy" -> binding.imageViewWeather.setImageResource(R.drawable.rainy)
            "snow" -> binding.imageViewWeather.setImageResource(R.drawable.snow)
            else -> binding.imageViewWeather.setImageResource(R.drawable.thunder)
        }
        //気分アイコンの表示
        when (diary.feeling) {
            "happy" -> binding.imageViewFeeling.setImageResource(R.drawable.happy)
            "smile" -> binding.imageViewFeeling.setImageResource(R.drawable.smile)
            "soso" -> binding.imageViewFeeling.setImageResource(R.drawable.soso)
            "angry" -> binding.imageViewFeeling.setImageResource(R.drawable.angry)
            else -> binding.imageViewFeeling.setImageResource(R.drawable.sad)
        }

        binding.textViewTitle.setText(diary.title)
        binding.textViewSentence.setText(diary.sentence)

        //編集ボタン
        binding.btnRewrite.setOnClickListener{
            // 書籍データを渡す処理(クリックしたときに何をしたいか)
            // 次のアクティビティに"diary"の中身のデータを渡す処理。※独自のclassなので簡単に渡せない
            val intent = Intent(this@DiaryActivity, RewriteDiaryActivity::class.java)
            // Gsonで Object → String
            intent.putExtra(DiaryActivity.KEY_STATE, Gson().toJson(diary))
            startActivity(intent)
        }

    }

}