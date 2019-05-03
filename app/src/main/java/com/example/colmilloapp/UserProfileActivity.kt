package com.example.colmilloapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
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
import kotlinx.android.synthetic.main.activity_user_profile.*
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
class UserProfileActivity: Fragment(),  BottomNavigationView.OnNavigationItemSelectedListener {
    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
    private var feedFotos: MutableList<Foto?>? = null
    private var param1: String? = null
    private var param2: String? = null
    private var listener: UserProfileActivity.OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        //setContentView(R.layout.activity_user_profile)
        //var stuff = arguments?.getParcelable<User>("user") as User
        //val user_profile = stuff as User
        var basura = ArrayList<String>()
        basura.add("Norbi es Puto")
        val user = User("3", "pedro","https://es.wikipedia.org/wiki/Canis_lupus#/media/File:Canis_lupus_265b.jpg", "CDMX", "Mexico", basura, basura)
        loadProfile(user)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        return inflater.inflate(R.layout.activity_user_profile, null)
    }
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }
    fun loadProfile(user_profile: User){

        val image_portada : ImageView = view!!.findViewById(R.id.imagePortada)
        val nombreUser : TextView = view!!.findViewById(R.id.nombreUser)
        val followersUser : TextView = view!!.findViewById(R.id.followersUser)
        val following : TextView = view!!.findViewById(R.id.followingUser)

        val options = RequestOptions().centerCrop().placeholder(R.drawable.load_card).error(R.drawable.load_card)

        //Glide.with(this).load(user_profile.imageProfile).apply(options).into(image_portada)
        nombreUser.setText(user_profile.nombre)
        followersUser.setText(user_profile.followers.size)
        followingUser.setText(user_profile.following.size)

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