package com.example.colmilloapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.colmilloapp.Models.CursoCard

class CursosCardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cursos_card)

        val cursoCard = intent.getSerializableExtra("CursoCard") as CursoCard
        loadCurso(cursoCard)
    }

    private fun loadCurso(cursoCard: CursoCard) {

        val name: TextView

        val image: ImageView
        name = findViewById(R.id.nombre)
        image = findViewById(R.id.image)

        val options = RequestOptions().centerCrop().placeholder(R.drawable.load_card).error(R.drawable.load_card)

        name.setText(cursoCard.nombre)

        Glide.with(this).load(cursoCard.image).apply(options).into(image)


    }

}
