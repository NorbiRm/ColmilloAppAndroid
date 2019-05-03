package com.example.colmilloapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
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
import kotlinx.android.synthetic.main.activity_user_profile.*
import kotlinx.android.synthetic.main.feed_foto.*

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
    private var view1: View? = null
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
        //setContentView(R.layout.activity_user_profile)
        //var stuff = arguments?.getParcelable<User>("user") as User
        //val user_profile = stuff as User



    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        this.view1 = LayoutInflater.from(container?.context).inflate(R.layout.activity_user_profile, container, false)
        this.image_portada =  this.view1!!.findViewById<View>(R.id.imagePortada) as ImageView
        this.nombreUser = this.view1!!.findViewById<View>(R.id.nombreUser) as TextView
        this.followersUser = this.view1!!.findViewById<View>(R.id.followersUser) as TextView
        this.following = this.view1!!.findViewById<View>(R.id.followingUser) as TextView

        var stuff = arguments
        this.user_profile = stuff?.getParcelable("user") as User
        loadProfile()
        Log.i("usuario", this.user_profile.toString())
       /* var basura = ArrayList<String>()
        basura.add("Norbi es Puto")
        val user = User("3", "pedro","https://es.wikipedia.org/wiki/Canis_lupus#/media/File:Canis_lupus_265b.jpg", "CDMX", "Mexico", basura, basura)
        loadProfile(user)*/
        return inflater.inflate(R.layout.activity_user_profile, null)
    }
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }
    fun loadProfile(){
        //Log.i("stuff", view!!.findViewById<ImageView>(R.id.imagePortada).toString()+" es esto")

        val options = RequestOptions().centerCrop().placeholder(R.drawable.load_card).error(R.drawable.load_card)
        Log.i("objetoUser",user_profile!!.imageProfile)
        Glide.with(this).load(user_profile!!.imageProfile).apply(options).into(image_portada!!)
        Log.i("objetoUser",user_profile!!.nombre)
        this.nombreUser!!.setText(user_profile!!.nombre)
        Log.i("objetoUser",user_profile!!.followers.size.toString())
        this.followersUser!!.setText(user_profile!!.followers.size.toString())
        Log.i("objetoUser",user_profile!!.following.size.toString())
        this.following!!.setText(user_profile!!.following.size.toString())

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