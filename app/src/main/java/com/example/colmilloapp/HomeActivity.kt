package com.example.colmilloapp

import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.example.colmilloapp.Models.Foto
import com.example.colmilloapp.R
import com.example.colmilloapp.RecyclerViews.HomeAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_home.*




private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
class HomeActivity : Fragment(),  BottomNavigationView.OnNavigationItemSelectedListener{
    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private var feedFotos: MutableList<Foto?>? = null
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_home)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        feedFotos = ArrayList<Foto?>()
        val rv = recyclerViewFeed

        jsonRequest()


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        return inflater.inflate(R.layout.activity_home, null)
    }
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }
    fun jsonRequest(){
        //var mDatabase = FirebaseDatabase.getInstance().reference
        var mReference = FirebaseDatabase.getInstance().getReference("Fotos")


        mReference!!.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapShot: DataSnapshot) {
                Log.i("feedFotos",dataSnapShot.childrenCount.toString())

                dataSnapShot.children.forEach {
                    feedFotos!!.add(it.getValue(Foto::class.java))
                }
                Log.i("feedArray",feedFotos.toString())
                setRecycleView(feedFotos!!)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // ...
            }
        })

    }
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }
    /*
    End Firebase
     */
    private fun setRecycleView(feedFotos:MutableList<Foto?>){
        val fotoRecyclerAdapter = HomeAdapter(BottomNavActivity(), feedFotos)
        recyclerViewFeed!!.layoutManager = LinearLayoutManager(BottomNavActivity())
        recyclerViewFeed!!.adapter = fotoRecyclerAdapter
    }

    companion object
    {
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
            HomeActivity().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }


}
