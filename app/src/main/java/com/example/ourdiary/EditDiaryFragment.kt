package com.example.ourdiary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.ourdiary.databinding.FragmentEditDiaryBinding
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
/*
private var DATE_RES_ID = "DATE_RES_ID"
private var TITLE_RES_ID = "TITLE_RES_ID"
private var SENTENCE_RES_ID = "SENTENCE_RES_ID"
*/

class EditDiaryFragment : Fragment() {
    private var _binding: FragmentEditDiaryBinding? = null
    private val binding get() = _binding!!

    private var dateText: EditText? = null
    private var titleText: EditText? = null
    private var sentenceText: EditText? = null
    private var dataText: EditText? = null

    private var db: FirebaseFirestore? =null
    private var diary: CollectionReference? = null

    //arguments用のリソースID
    /*
    private var dateResId: String? = null
    private var titleResId: String? = null
    private var sentenceResId: String? = null
     */

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        /*
        arguments?.let {
            dateResId = it.getString(DATE_RES_ID)
            titleResId = it.getString(TITLE_RES_ID)
            sentenceResId = it.getString(SENTENCE_RES_ID)
        }

         */

        binding.buttonConf.setOnClickListener{ doConfirm(it) }

        /*
        dateText = findViewById(R.id.editTextDate)
        titleText = findViewById(R.id.editTextTitle)
        sentenceText = findViewById(R.id.editTextSentence)

         */

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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_diary, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    }

    fun showDateDialog(view:View){
        DateDialog{ date ->
            binding.editTextDate.setText(date)
        } .show( parentFragmentManager, "date_dialog")
    }

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
                    context, "日記を更新しました!",
                    Toast.LENGTH_SHORT
                ).show()

            })
            .addOnFailureListener(OnFailureListener {
                Toast.makeText(
                    context, "更新できませんでした。",
                    Toast.LENGTH_SHORT
                ).show()
            })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /*
    companion object{
        @JvmStatic
        fun newInstance() =
            EditDiaryFragment().apply {
                arguments = Bundle().apply{
                    putString(DATE_RES_ID, dateText.toString())
                    putString(TITLE_RES_ID, titleText.toString())
                    putString(SENTENCE_RES_ID, sentenceText.toString())
                }
            }
    }

     */
}