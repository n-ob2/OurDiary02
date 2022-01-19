package com.example.ourdiary

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson

class DiaryActivity : AppCompatActivity() {

    companion object {
        val KEY_STATE = "key_state"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diary)

        val stateStr = intent.getStringExtra(KEY_STATE)
        // Gsonで String → Object
        val diary = Gson().fromJson<Diary>(stateStr, Diary::class.java)
        //変数diaryの中に諸々の日記データが格納されているから、bindingで各ViewにsetTextすればよい。
        print(diary)
    }
}