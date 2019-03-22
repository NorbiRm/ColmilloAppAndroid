package com.example.colmilloapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.LinearLayout
import android.widget.TextView
import com.example.colmilloapp.Models.Foto
import com.example.colmilloapp.R
import com.example.colmilloapp.RecyclerViews.HomeAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    private var feedFotos: MutableList<Foto?>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        feedFotos = ArrayList<Foto?>()
        val rv = recyclerViewFeed
        rv.setHasFixedSize(true)
        jsonRequest()

        // Aqui irían todos los atributos del perfil
        //var grid = GridLayoutManager(this, 4)
        // rv.layoutManager = grid

    }
    /*
        Firebase
         */
    fun jsonRequest(){
        //var mDatabase = FirebaseDatabase.getInstance().reference
        var mReference = FirebaseDatabase.getInstance().getReference("Fotos")

        val nListener = object: ValueEventListener{

            override fun onDataChange(dataSnapShot: DataSnapshot) {
                Log.i("feedFotos",dataSnapShot.childrenCount.toString())

                dataSnapShot.children.forEach {
                    feedFotos!!.add(it.getValue(Foto::class.java))
                }
                Log.i("feedArray",feedFotos.toString())
                setRecycleView(feedFotos!!)
            }

            override fun onCancelled(p0: DatabaseError) {
            }
        }
        mReference!!.addValueEventListener(nListener)

    }
    /*
    End Firebase
     */
    private fun setRecycleView(feedFotos:MutableList<Foto?>){
        val fotoRecyclerAdapter = HomeAdapter(this, feedFotos)
        recyclerViewFeed!!.layoutManager = LinearLayoutManager(this)
        recyclerViewFeed!!.adapter = fotoRecyclerAdapter
    }


}
