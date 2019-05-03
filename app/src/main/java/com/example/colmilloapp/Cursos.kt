package com.example.colmilloapp

import android.itesm.edu.Curso.adapters.CursosRecyclerAdapter
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.example.colmilloapp.Models.CursoCard
import com.example.colmilloapp.Models.Foto
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_cursos.*
import kotlinx.android.synthetic.main.activity_home.*
import java.util.*
import kotlin.collections.ArrayList

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
class Cursos :  Fragment(),  BottomNavigationView.OnNavigationItemSelectedListener {
    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private var request: JsonObjectRequest? = null
    private var requestQueue: RequestQueue? = null
    private var cards: MutableList<CursoCard?>? = null


    private var listener: HomeActivity.OnFragmentInteractionListener? = null
    private var param1: String? = null
    private var param2: String? = null

    private var mDatabase: DatabaseReference? = null
    private var mMessageReference: DatabaseReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        cards = ArrayList<CursoCard?>()
        val rv = recyclerViewFeed
        //rv.setHasFixedSize(true)
        jsonrequest()

        Log.i("Cursos","Content Set")

       




    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        return inflater.inflate(R.layout.activity_cursos, null)

    }

    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }
    private fun jsonrequest() {

        // ...
        mDatabase = FirebaseDatabase.getInstance().reference
        mMessageReference = FirebaseDatabase.getInstance().getReference("cursos")

        cards = ArrayList<CursoCard?>();
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

    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    private fun setRecyclerView(cursoCards: MutableList<CursoCard?>) {
        val cursosRecyclerAdapter = CursosRecyclerAdapter(BottomNavActivity(), cursoCards)
        recyclerView!!.layoutManager = LinearLayoutManager(BottomNavActivity())
        recyclerView!!.adapter = cursosRecyclerAdapter


    }

    private fun writeNewCurso(id:String,nombre:String,fechaInicio:String,fechaFin:String,cupo:Int,image:String) {
        val user = CursoCard(id,nombre,fechaInicio,fechaFin,cupo,image)

        mDatabase!!.child("cursos").child(id).setValue(user)
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AquamanFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Cursos().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
