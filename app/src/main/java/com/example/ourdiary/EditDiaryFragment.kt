package com.example.ourdiary

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.ourdiary.databinding.FragmentEditDiaryBinding
import com.google.firebase.firestore.CollectionReference
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
    private var feelingText: String? = null
    private var weatherText: String? = null
    private var titleText: EditText? = null
    private var sentenceText: EditText? = null
    private var dataText: EditText? = null

    private lateinit var userId: String

    private var db: FirebaseFirestore? = null
    private var diary: CollectionReference? = null

    //arguments用のリソースID
    /*
    private var dateResId: String? = null
    private var titleResId: String? = null
    private var sentenceResId: String? = null
     */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        db = FirebaseFirestore.getInstance()
        diary = db!!.collection("users")

        //val data = getSharedPreferences("UserIdDataStore", Context.MODE_PRIVATE)
        val preferences = context?.getSharedPreferences("userInfo", Activity.MODE_PRIVATE)
        userId = preferences?.getString("UserId", "").toString()

        diary!!.addSnapshotListener { snapshot, e ->
            if(snapshot!= null) {
                var result = ""
                val items: Iterator<QueryDocumentSnapshot> = snapshot!!.iterator()
                while (items.hasNext()) {
                    val docdata = items.next()
                    val data = docdata.data
                    result += """${data["date"].toString()} ${data["feeling"].toString()} ${data["title"].toString()} ${data["sentence"].toString()}
    """
                }
                dataText?.setText(result)
            }
        }

    }   //onCreate↑↑

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentEditDiaryBinding.inflate(inflater, container, false)
        binding.buttonConf.setOnClickListener { doConfirm(it) }
        binding.editTextDate.setOnClickListener { showDateDialog(it) }

        return binding.root
        //return inflater.inflate(R.layout.fragment_edit_diary, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        //各気分のラジオボタンが選ばれた時に変数feelingTextに値を代入
        binding.radioGroupFeelings.setOnCheckedChangeListener{ group, checkedId: Int ->
            when (checkedId) {
                R.id.radioButtonHappy ->  feelingText = "happy"
                R.id.radioButtonSmile -> feelingText = "smile"
                R.id.radioButtonSoso -> feelingText = "soso"
                R.id.radioButtonAngry -> feelingText = "angry"
                else -> feelingText = "sad"
            }
        }
        //各天気のラジオボタンが選ばれた時に変数weatherTextに値を代入
        binding.radioGroupeWeather.setOnCheckedChangeListener{ group, checkedId: Int ->
            when (checkedId) {
                R.id.radioButtonSunny ->  weatherText = "sunny"
                R.id.radioButtonCloudy -> weatherText = "cloudy"
                R.id.radioButtonRainy -> weatherText = "rainy"
                R.id.radioButtonSnow -> weatherText = "snow"
                else -> weatherText = "thunder"
            }
        }

        /*
        binding.editTextDate.setOnClickListener {
            DateDialog { date ->
                binding.editTextDate.setText(date)
            }.show(parentFragmentManager,"date_dialog")
        }*/
    }

    fun showDateDialog( view:View ) {
        DateDialog { date ->
            binding.editTextDate.setText(date)
        }.show(parentFragmentManager, "date_dialog")
    }

    private fun doConfirm(view: View?) {
        val date: String = binding.editTextDate!!.getText().toString()
        val feel: String = feelingText.toString()
        val weather: String = weatherText.toString()
        val tit: String = binding.editTextTitle!!.getText().toString()
        val sen: String = binding.editTextSentence!!.getText().toString()
        val data: MutableMap<String, Any> = HashMap()
        data["date"] = date
        data["feeling"] = feel
        data["weather"] = weather
        data["title"] = tit
        data["sentence"] = sen
        diary!!.add(data)
            .addOnSuccessListener{
                Toast.makeText(
                    context, "日記を作成しました!",
                    Toast.LENGTH_LONG
                ).show()
                val intent = Intent(activity, CalendarActivity::class.java)
                startActivity(intent)
            }
            .addOnFailureListener{
                Toast.makeText(
                    context, "更新できませんでした。",
                    Toast.LENGTH_LONG
                ).show()
            }
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