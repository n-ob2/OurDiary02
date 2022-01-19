package com.example.ourdiary

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class DiaryActivity : AppCompatActivity() {

    companion object {
        val KEY_STATE = "key_state"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diary)

        val diary = intent.getSerializableExtra(KEY_STATE)
        //変数diaryの中に諸々の日記データが格納されているから、bindingで各ViewにsetTextすればよい。
    }
}