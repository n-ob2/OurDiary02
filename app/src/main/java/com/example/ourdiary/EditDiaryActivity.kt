package com.example.ourdiary

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ourdiary.databinding.ActivityEditDiaryBinding


class EditDiaryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditDiaryBinding
    private lateinit var editDiary: EditDiaryFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditDiaryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        editDiary = EditDiaryFragment()
        supportFragmentManager.beginTransaction().apply{
            replace(R.id.fragmentEditDiaryContainer, editDiary)
            commit()
        }

    }   //onCreate↑↑

    /*
    private fun String.toDate(pattern: String = "yyyy/MM/dd"): Date?{
        return try{
            SimpleDateFormat(pattern).parse(this)
        } catch (e: IllegalThreadStateException){
            return null
        } catch (e: ParseException){
            return null
        }
    }

     */
}