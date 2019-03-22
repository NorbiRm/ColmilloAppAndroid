package com.example.colmilloapp

import android.itesm.edu.Curso.adapters.CursosRecyclerAdapter
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.example.colmilloapp.Models.CursoCard
import com.google.firebase.database.*
import java.util.*


class Cursos : AppCompatActivity() {



    private var request: JsonObjectRequest? = null
    private var requestQueue: RequestQueue? = null
    private var cards: MutableList<CursoCard?>? = null
    private var recyclerView: RecyclerView? = null

    private var mDatabase: DatabaseReference? = null
    private var mMessageReference: DatabaseReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cursos)

        Log.i("Cursos","Content Set")

        cards = ArrayList<CursoCard?>()
        recyclerView = findViewById(R.id.recyclerView)
        Log.i("Cursos","Data declared")
        jsonrequest()
        Log.i("Cursos","json request end")

    }

    private fun jsonrequest() {

        // ...
        mDatabase = FirebaseDatabase.getInstance().reference
        mMessageReference = FirebaseDatabase.getInstance().getReference("cursos")


        val messageListener = object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                Log.i("Curso","in firebase")

                Log.i("Curso","in if firebase")

                Log.i("Curso",dataSnapshot.childrenCount.toString())

                dataSnapshot.children.forEach {
                    cards!!.add(it.getValue(CursoCard::class.java))

                }

                Log.i("Curso",cards!!.toString())
                setRecyclerView(cards!!)

                Log.i("Curso","end if firebase")

            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Failed to read value
            }
        }

        mMessageReference!!.addValueEventListener(messageListener)



        Log.i("Curso","done firebase")

    }

    private fun setRecyclerView(cursoCards: MutableList<CursoCard?>) {
        val cursosRecyclerAdapter = CursosRecyclerAdapter(this, cursoCards)
        recyclerView!!.layoutManager = LinearLayoutManager(this)
        recyclerView!!.adapter = cursosRecyclerAdapter


    }

    private fun writeNewCurso(id:String,nombre:String,fechaInicio:String,fechaFin:String,cupo:Int,image:String) {
        val user = CursoCard(id,nombre,fechaInicio,fechaFin,cupo,image)

        mDatabase!!.child("cursos").child(id).setValue(user)
    }
}
