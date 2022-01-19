package com.example.ourdiary

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ourdiary.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth


class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.textResetPass.setOnClickListener{ onTextResetPassTapped(it) }

        val buttonSignUp = findViewById<Button>(R.id.btnSignUp)
        val buttonLogin = findViewById<Button>(R.id.btnLogin)



        buttonSignUp.setOnClickListener {   //新規登録

            var emailEditText = binding.editEmail.text.toString()
            var passEditText = binding.editPassword.text.toString()

            auth.createUserWithEmailAndPassword(emailEditText, passEditText)
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

        buttonLogin.setOnClickListener {    //ログイン

            var emailEditText = findViewById<EditText>(R.id.editEmail)
            var emailText = emailEditText.text.toString()

            var passEditText = findViewById<EditText>(R.id.editPassword)
            var passText = passEditText.text.toString()

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
            //val intent = Intent(this, CalendarActivity::class.java)
            //startActivity(intent)
        }

    }   //onCreate ↑↑

    fun onTextResetPassTapped(view: View?){
        val intent = Intent(this, PasswordlessActivity::class.java)
        startActivity(intent)
    }




    override fun onStart() {
        super.onStart()

    }   //onStart↑↑

    fun doSignUp(view: View?){

    }//doSignUp↑↑



    fun doLogin(view: View?) {
    }   //doLogin↑↑

}
