package com.example.ourdiary

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ourdiary.databinding.ActivityEditDiaryBinding
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import java.util.*
import kotlin.collections.HashMap


class EditDiaryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditDiaryBinding

    private var dateText: EditText? = null
    private var titleText: EditText? = null
    private var sentenceText: EditText? = null
    private var dataText:EditText? = null

    private var db: FirebaseFirestore? =null
    private var diary: CollectionReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditDiaryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonConf.setOnClickListener{ doConfirm(it) }

        dateText = findViewById(R.id.editTextDate)
        titleText = findViewById(R.id.editTextTitle)
        sentenceText = findViewById(R.id.editTextSentence)

        db = FirebaseFirestore.getInstance()
        diary = db!!.collection("diary")

        diary!!.addSnapshotListener { snapshot, e ->
            var result = ""
            val items: Iterator<QueryDocumentSnapshot> = snapshot!!.iterator()
            while (items.hasNext()) {
                val docdata = items.next()
                val data = docdata.data
                result += """${data["date"].toString()} ${data["title"].toString()} ${data["sentence"].toString()}
"""
            }
            dataText?.setText(result)
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

    private fun doConfirm(view: View?) {
        val date: String = dateText!!.getText().toString()
        val tit: String = titleText!!.getText().toString()
        val sen: String = sentenceText!!.getText().toString()
        val data: MutableMap<String, Any> = HashMap()
        data["date"] = date
        data["title"] = tit
        data["sentence"] = sen
        diary!!.add(data)
            .addOnSuccessListener(OnSuccessListener<DocumentReference?> {
                Toast.makeText(
                    this@EditDiaryActivity, "日記を更新しました!",
                    Toast.LENGTH_SHORT
                ).show()
            })
            .addOnFailureListener(OnFailureListener {
                Toast.makeText(
                    this@EditDiaryActivity, "更新できませんでした。",
                    Toast.LENGTH_SHORT
                ).show()
            })
    }




}