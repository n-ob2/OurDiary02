package com.n_ob2.ourdiary

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.n_ob2.ourdiary.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth


class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        //binding.textResetPass.setOnClickListener{ onTextResetPassTapped(it) }

        val buttonSignUp = findViewById<Button>(R.id.btnSignUp)
        val buttonLogin = findViewById<Button>(R.id.btnLogin)

        buttonSignUp.setOnClickListener {   //新規登録

            var emailEditText = binding.editEmail.text.toString() + ""
            var passEditText = binding.editPassword.text.toString() + ""

            var length: Int = passEditText.length

            try {
                auth.createUserWithEmailAndPassword(emailEditText, passEditText)
                    .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {
                                //ユーザーのIDをアプリ内に保存する処理↓↓
                                // "DataStore"という名前でインスタンスを生成
                                val dataStore: SharedPreferences = getSharedPreferences("UserIdDataStore", Context.MODE_PRIVATE)
                                // ユーザーIDを取得
                                val userId = auth.currentUser?.uid
                                // 文字列を"UserId"に書き込む
                                val editor = dataStore.edit()
                                editor.putString("UserId", userId)
                                // ユーザーIDを保存する
                                editor.commit()


                                Log.d("DEBUG", "登録できました。")
                                Toast.makeText(baseContext, "登録できました。", Toast.LENGTH_LONG).show()
                                val intent = Intent(this, CalendarActivity::class.java)
                                startActivity(intent)
                            } else if ( length < 6 ) {
                                Log.d("DEBUG", "createUserWithEmail:failure", task.exception)
                                Toast.makeText(baseContext, "パスワードは6文字以上です。", Toast.LENGTH_LONG).show()

                            } else {
                                Log.d("DEBUG", "createUserWithEmail:failure", task.exception)
                                Toast.makeText(baseContext, "登録に失敗しました。", Toast.LENGTH_SHORT).show()
                            }
                    }
            } catch (e: Exception){
                Toast.makeText(baseContext, "登録に失敗しました。", Toast.LENGTH_LONG).show()
            }

        }

        buttonLogin.setOnClickListener {    //ログイン

            val email = binding.editEmail.text.toString() + ""
            val password: String = binding.editPassword.text.toString() + ""

            //Toast.makeText(this@MainActivity, "ログイン スタート", Toast.LENGTH_SHORT).show()

            try {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {

                            //ユーザーIDを取得する処理↓↓
                            // "DataStore"という名前でインスタンスを生成
                            val dataStore: SharedPreferences = getSharedPreferences("UserIdDataStore", Context.MODE_PRIVATE)
                            // ユーザーIDを取得
                            val userId = auth.currentUser?.uid
                            // 文字列を"UserId"に書き込む
                            val editor = dataStore.edit()
                            editor.putString("UserId", userId)
                            // ユーザーIDを保存する
                            editor.commit()

                            // ログイン成功
                            Toast.makeText(this@MainActivity, "ログインしました!!", Toast.LENGTH_SHORT)
                                .show()
                            //val user = auth.currentUser
                            val intent = Intent(this, CalendarActivity::class.java)
                            startActivity(intent)
                            //                    updateUI(user)
                        } else {
                            // ログイン失敗
                            Toast.makeText(this@MainActivity, "ログインできませんでした。", Toast.LENGTH_SHORT)
                                .show()
                            //                    updateUI(null)
                        }
                    }
            }catch (e: Exception){
                Toast.makeText(baseContext, "ログインできませんでした。", Toast.LENGTH_LONG).show()

            }

        }

    }   //onCreate ↑↑

    fun onTextResetPassTapped(view: View?){
        val intent = Intent(this, CalendarActivity::class.java)
        startActivity(intent)
    }




    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser

    }   //onStart↑↑

    fun doSignUp(view: View?){

    }//doSignUp↑↑



    fun doLogin(view: View?) {
    }   //doLogin↑↑

}
