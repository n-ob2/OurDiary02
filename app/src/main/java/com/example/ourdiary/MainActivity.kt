package com.example.ourdiary

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth


class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()

        val buttonSignUp = findViewById<Button>(R.id.btnSignUp)
        val buttonLogin = findViewById<Button>(R.id.btnLogin)

        buttonSignUp.setOnClickListener {
            val emailEditText = findViewById<EditText>(R.id.editEmail)
            val emailText = emailEditText.text.toString()
            val passEditText = findViewById<EditText>(R.id.editPassword)
            val passText = passEditText.text.toString()

            auth.createUserWithEmailAndPassword(emailText, passText)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(
                            baseContext, "SignUp 成功",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            baseContext, "SignUp 失敗",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }

        buttonLogin.setOnClickListener {

            val emailEditText = findViewById<EditText>(R.id.editEmail)
            val emailText = emailEditText.text.toString()

            val passEditText = findViewById<EditText>(R.id.editPassword)
            val passText = passEditText.text.toString()

            auth.signInWithEmailAndPassword(emailText, passText)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(
                            baseContext, "Login 成功",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            baseContext, "Login 失敗",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }

    }   //onCreate ↑↑






    override fun onStart() {
        super.onStart()

    }   //onStart↑↑

    fun doSignUp(view: View?){

    }//doSignUp↑↑



    fun doLogin(view: View?) {
    }   //doLogin↑↑

}