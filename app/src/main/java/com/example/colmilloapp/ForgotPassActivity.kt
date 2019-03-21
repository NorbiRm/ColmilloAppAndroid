package com.example.colmilloapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_forgot_pass.*

class ForgotPassActivity : AppCompatActivity() {

    private lateinit var email: EditText
    private lateinit var auth: FirebaseAuth
    private lateinit var progressBar: ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_pass)
        email = findViewById(R.id.EmailRestablish)
        //auth = FirebaseAuth.getInstance()
        progressBar = findViewById(R.id.progressBarForget)
    }
    fun send(view: View){
        val temail = email.text.toString()
        if(!TextUtils.isEmpty(temail))
        {
            FirebaseAuth.getInstance().sendPasswordResetEmail(temail).addOnCompleteListener(this){
                task->
                if(task.isSuccessful){
                    progressBarForget.visibility=View.VISIBLE
                    startActivity(Intent(this, LoginActivity::class.java))
                }
                else
                {
                    Toast.makeText(this, "Error al restablecer contraseña", Toast.LENGTH_LONG).show()
                }
            }
        }

    }
}
