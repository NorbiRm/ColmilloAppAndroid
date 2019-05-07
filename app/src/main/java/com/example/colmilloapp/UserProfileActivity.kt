package com.example.colmilloapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.colmilloapp.Models.Foto
import com.example.colmilloapp.Models.User
import com.example.colmilloapp.RecyclerViews.HomeAdapter
import com.example.colmilloapp.RecyclerViews.UserFotosAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_user_profile.*
import kotlinx.android.synthetic.main.feed_foto.*

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class UserProfileActivity: Fragment(),  BottomNavigationView.OnNavigationItemSelectedListener {
    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
    private var UserProfileFotos : MutableList<Foto?>? = null
    private var param1: String? = null
    private var param2: String? = null

    private var listener: UserProfileActivity.OnFragmentInteractionListener? = null

    private var image_portada: ImageView? = null
    private var nombreUser : TextView? = null
    private var followersUser : TextView? = null
    private var following : TextView? = null
    private var user_profile: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        UserProfileFotos = ArrayList<Foto?>()

    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        return inflater.inflate(R.layout.activity_user_profile,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadProfile(view)
    }
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }
    fun loadProfile(view:View){

        Log.i("UserProfile", view.findViewById<ImageView>(R.id.imagePortada).toString())

        jsonRequest()
        this.image_portada =  view.findViewById<View>(R.id.imagePortada) as ImageView
        this.nombreUser = view.findViewById<View>(R.id.nombreUser) as TextView
        this.followersUser = view.findViewById<View>(R.id.followersUser) as TextView
        this.following = view.findViewById<View>(R.id.followingUser) as TextView

        var stuff = arguments
        this.user_profile = stuff?.getParcelable("user") as User

        Log.i("UserProfile", this.user_profile.toString())
        val options = RequestOptions().centerCrop().placeholder(R.drawable.load_card).error(R.drawable.load_card)

        Log.i("UserProfile",user_profile!!.imageProfile)
        Glide.with(this).load(user_profile!!.imageProfile).apply(options).into(image_portada!!)

        Log.i("UserProfile",user_profile!!.nombre)
        this.nombreUser!!.setText(user_profile!!.nombre)

        Log.i("UserProfile",user_profile!!.followers.size.toString())
        this.followersUser!!.setText(user_profile!!.followers.size.toString())

        Log.i("UserProfile",user_profile!!.following.size.toString())
        this.following!!.setText(user_profile!!.following.size.toString())


    }
    fun jsonRequest(){
        //var mDatabase = FirebaseDatabase.getInstance().reference

        var mReference = FirebaseDatabase.getInstance().getReference("Fotos")


        mReference!!.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapShot: DataSnapshot) {
                Log.i("feedFotos",dataSnapShot.childrenCount.toString())

                dataSnapShot.children.forEach {
                    var fotoTemp = it.getValue(Foto::class.java) as Foto
                    if(fotoTemp.idUser == "1") {
                        Log.i("fotoTemp","caraja")
                        UserProfileFotos!!.add(fotoTemp)
                    }
                }
                Log.i("feedArray",UserProfileFotos.toString())
                setRecycleView(UserProfileFotos!!)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // ...
            }
        })

    }

    private fun setRecycleView(UserProfileFotos:MutableList<Foto?>){
        val fotoRecyclerAdapter = UserFotosAdapter(BottomNavActivity(), UserProfileFotos)
        recyclerViewFotosUser!!.layoutManager = GridLayoutManager(BottomNavActivity(),4)
        recyclerViewFotosUser!!.adapter = fotoRecyclerAdapter
    }

    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }
    companion object
    {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
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