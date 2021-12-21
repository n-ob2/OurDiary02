package com.example.ourdiary

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ourdiary.databinding.ActivityEditDiaryBinding


class EditDiaryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditDiaryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditDiaryBinding.inflate(layoutInflater)
        setContentView(binding.root)

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