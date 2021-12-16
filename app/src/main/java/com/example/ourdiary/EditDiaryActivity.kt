package com.example.ourdiary

import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot

class EditDiaryActivity : AppCompatActivity() {

    private var dateText: EditText? = null
    private var titleText: EditText? = null
    private var sentenceText: EditText? = null
    private var dataText:EditText? = null

    private var db: FirebaseFirestore? =null
    private var diary: CollectionReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_diary)

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
                result += """${data["date"].toString()}$ {data["title"].toString()} ${data["sentence"].toString()}
"""
            }
            dataText?.setText(result)
        }
    }   //onCreate↑↑

    private fun doConfirm(view: View?) {
        val date: String = dateText!!.getText().toString() + ""//(dateText!!.getText().toString() + "").toInt()
        val tit: String = titleText!!.getText().toString() + ""
        val sen: String = sentenceText!!.getText().toString() + ""
        val data: MutableMap<String, Any> = HashMap()
        data["date"] = date
        data["title"] = tit
        data["sentence"] = sen
        diary!!.add(data)
            .addOnSuccessListener(OnSuccessListener<DocumentReference?> {

            })
            .addOnFailureListener(OnFailureListener {

            })
    }
}