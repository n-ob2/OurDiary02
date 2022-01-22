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

    }//onCreate ↑↑
}