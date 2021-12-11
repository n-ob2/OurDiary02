package com.example.ourdiary

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.ourdiary.databinding.FragmentResetPassBinding
import com.google.firebase.auth.ActionCodeSettings
import com.google.firebase.auth.FirebaseAuth


class ResetPassFragment : Fragment() {
    private var _binding: FragmentResetPassBinding? = null
    private val binding get() = _binding!!

    lateinit var auth: FirebaseAuth
    val user = auth.currentUser!!

    val url = "http://www.example.com/verify?uid=" + user.uid
    val actionCodeSettings = ActionCodeSettings.newBuilder()
        .setUrl(url)
        .setAndroidPackageName("com.example.android", false, null)
        .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }

        user.sendEmailVerification(actionCodeSettings)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "メールを送信しました。.")
                }
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentResetPassBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {

        fun newInstance(param1: String, param2: String) =
            ResetPassFragment().apply {
                arguments = Bundle().apply {


                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}