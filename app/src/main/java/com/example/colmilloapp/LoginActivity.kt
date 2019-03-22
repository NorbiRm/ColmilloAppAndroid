package com.example.colmilloapp

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

import com.example.colmilloapp.Cursos


class LoginActivity : AppCompatActivity() {

    private lateinit var progressBar: ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        progressBar = findViewById(R.id.login_progress)

        email_sign_in_button.setOnClickListener{
            doLogin()
        }
        forgotPass.setOnClickListener{
            ForgotMyPass()
        }

    }
    private fun doLogin(){
        val email = email.text.toString()
        val password = password.text.toString()

        if(email.isEmpty())
        {
            Toast.makeText(this, "Por favor ingresar Email", Toast.LENGTH_SHORT).show()
        }
        else if(password.isEmpty())
        {
            Toast.makeText(this, "Por favor ingresar Contraseña", Toast.LENGTH_SHORT).show()
        }
        else
        {
            progressBar.visibility = View.VISIBLE
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener{
                if(!it.isSuccessful) return@addOnCompleteListener
                Toast.makeText(applicationContext, "id: ${it.result!!.user.uid}", Toast.LENGTH_LONG).show()
                val intent = Intent(this, Cursos::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }.addOnFailureListener {
                Toast.makeText(applicationContext, "id: ${it.message}", Toast.LENGTH_LONG).show()
            }
        }
    }
    private fun ForgotMyPass(){
        val intent = Intent(this, ForgotPassActivity::class.java)
        startActivity(intent)
    }
}
