package com.example.ourdiary

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.ourdiary.databinding.FragmentGachaDiaryBinding
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot

class GachaDiaryFragment : Fragment() {
    private var _binding: FragmentGachaDiaryBinding?= null
    private val binding get() = _binding!!

    private var dateText: EditText? = null
    private var textGacha: EditText? = null
    private var editTextAnswer: EditText? = null
    private var dataText: EditText? = null

    private var db: FirebaseFirestore? =null
    private var diary: CollectionReference? = null

    //ガチャの質問リスト
    val list : Array<String> = arrayOf(
        "いちばんたのしかったことは？",
        "どんなお手伝い（おてつだい）をした？",
        "お昼ごはんは何を食べた？",
        "今日ならった漢字は？むずかしかった？",
        "なんのテレビを見た？",
        "おそうじ当番（とうばん）はどこのばしょ？",
        "休みじかんはどんなことをしたの？",
        "今だいすきなことは何？",
        "さんすうでならったことを教えて！",
        "どんなおやつたべたの？",
        "明日（あした）は何してあそびたい？",
        "今日はじめて知ったこと！",
        "なにがかりをしてるの？どんな仕事（しごと）があるのかな？"
    )



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
        _binding = FragmentGachaDiaryBinding.inflate(inflater, container, false)
        binding.buttonGachaRegistration.setOnClickListener { doConfirm(it)}
        binding.buttonGacha.setOnClickListener { doGacha(it) }


        //タイトルガチャ
        var range = (0..list.size)
        var result = range.random()
        var resultTitle = list[result]
        binding.textGacha.setText(resultTitle)

        return binding.root
        //return inflater.inflate(R.layout.fragment_gacha_diary, container, false)
    }

    fun showDateDialog(view:View){
        DateDialog{ date ->
            binding.editTextDate.setText(date)
        } .show( parentFragmentManager, "date_dialog")
    }

    private fun doConfirm(view: View?) {
        val date: String = dateText!!.getText().toString()
        val tit: String = textGacha!!.getText().toString()
        val sen: String = editTextAnswer!!.getText().toString()
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
                val intent = Intent(activity, CalendarActivity::class.java)
                startActivity(intent)
            })
            .addOnFailureListener(OnFailureListener {
                Toast.makeText(
                    context, "更新できませんでした。",
                    Toast.LENGTH_SHORT
                ).show()
            })
    }

    private fun doGacha(view: View?){
        var range = (0..list.size)
        var result = range.random()
        var resultTitle = list[result]
        binding.textGacha.setText(resultTitle)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

    }
}