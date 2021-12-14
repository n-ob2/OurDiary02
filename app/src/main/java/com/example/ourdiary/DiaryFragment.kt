package com.example.ourdiary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.ourdiary.databinding.FragmentDiaryBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DiaryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DiaryFragment : Fragment() {
    private var _binding: FragmentDiaryBinding? = null
    private val binding get() = _binding!!

    private var dateText: TextView? = null
    private var titleText: TextView? = null
    private var diaryText: TextView? = null
    private var diary = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = FirebaseFirestore.getInstance()
        diary = db.collection("diary")

        diary.get().addOnCompleteListener(OnCompleteListener<QuerySnapshot?> { task ->
            if (task.isSuccessful) {
                for (document in task.result) {
                    val data = document.data
                    binding.textDate.setText(data?.date)
                    binding.textTitle.setText(data?.title)
                    binding.textSentence.setText(data?.sentence)
                    /*
                    result += (data["name"].toString() + " ["
                            + data["mail"].toString() + ":"
                            + data["age"].toString() + "]\n")
                     */
                }
                //dateText?.setText(result)
            } else {
                dateText?.setText("データが読み込めません。")
            }
        })

    }   //onCreate↑↑

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDiaryBinding.inflate(inflater, container, false)
        return binding.root


    }   //onCreateView
}