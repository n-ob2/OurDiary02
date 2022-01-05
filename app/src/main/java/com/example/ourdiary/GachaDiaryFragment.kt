package com.example.ourdiary

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.ourdiary.databinding.FragmentGachaDiaryBinding
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import kotlin.random.Random

class GachaDiaryFragment : Fragment() {
    private var _binding: FragmentGachaDiaryBinding?= null
    private val binding get() = _binding!!

    private var dateText: EditText? = null
    private var titleText: EditText? = null
    private var sentenceText: EditText? = null
    private var dataText: EditText? = null

    private var textGacha01: TextView? =null

    private var db: FirebaseFirestore? =null
    private var diary: CollectionReference? = null

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

        //ガチャの質問リスト
        val List : Array<String> = arrayOf(
            "いちばんたのしかったことは？",
            "どんなお手伝い（おてつだい）をした？",
            "お昼ごはんは何を食べた？",
            "今日ならった漢字は？",
            "なんのテレビを見た？",
            "おそうじ当番（とうばん）はどこのばしょ？",
            "休みじかんはどんなことをしたの？",
            "今だいすきなことは何？",
            "さんすうでならったことを教えて！",
            "どんなおやつたべたの？",
            "明日（あした）は何してあそびたい？",
            "今日はじめて知ったこと！"
        )

    }   //onCreate↑↑



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentGachaDiaryBinding.inflate(inflater, container, false)
        binding.buttonConf.setOnClickListener { doConfirm(it)}

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

    }

    /**
     * リストから指定された数の要素をランダムに重複なく取り出す。
     *
     * @receiver     このリストから要素を取り出す。
     * @param n      取り出す要素の数。
     * @param random 使用する乱数生成器。
     * @param <T>    要素の型。
     * @return 取得された要素を持つリスト。
     */

    fun <String> Collection<kotlin.String>.takeAtRandom(n: Int, random: Random = Random): List<kotlin.String> {
        require(n <= size) { "引数 n の値 $n がレシーバーのサイズ ${size} より大きいです。" }

        val taken = mutableListOf<kotlin.String>() // ランダムに選択された要素を持たせるリスト

        val remaining = toMutableList() // 残っている要素のリスト
        // 3 回繰り返す。
        repeat(n) {
            val remainingCount = remaining.size // 残っている要素の数
            val index = random.nextInt(remainingCount) // ランダムに選択されたインデックス

            taken += remaining[index] // ランダムに選択された要素

            val lastIndex = remainingCount - 1 // 残っている要素のリストの末尾のインデックス
            val lastElement = remaining.removeAt(lastIndex) // 残っている要素のリストから末尾を削除する。
            if (index < lastIndex) { // ランダムに選択された要素が末尾以外なら…
                remaining[index] = lastElement // それを末尾の要素で置換する。
            }
        }

        return taken
    }
}