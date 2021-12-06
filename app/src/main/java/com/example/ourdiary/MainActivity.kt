package com.example.ourdiary

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


class MainActivity : AppCompatActivity() {
    private var emailLogin: EditText? = null
    private var passLogin: EditText? = null
    private val mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        emailLogin = findViewById(R.id.editTextLoginEmail);
        passLogin = findViewById(R.id.editTextLoginPassword);

    }   //onCreate ↑↑


    override fun onStart() {
        super.onStart()
        val currentUser = mAuth?.currentUser
        updateUI(currentUser)
    }   //onStart↑↑

    fun updateUI(user: FirebaseUser?) {
        /*
        if (user == null) {
            labelView.setText("no login...")
        } else {
            labelView.setText("login: " + user.email)
        }
         */
    }

    fun doLogin(view: View?) {
        val email: String = emailLogin?.getText().toString() + ""
        val password: String = passLogin?.getText().toString() + ""
        Toast.makeText(
            this@MainActivity, "Login start.",
            Toast.LENGTH_SHORT
        ).show()
        mAuth!!.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(
                this
            ) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        this@MainActivity, "Logined!!",
                        Toast.LENGTH_SHORT
                    ).show()
                    val user = mAuth.currentUser
                    updateUI(user)
                } else {
                    Toast.makeText(
                        this@MainActivity, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                    updateUI(null)
                }
            }
    }   //doLogin↑↑

}