package com.example.colmilloapp

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.example.colmilloapp.Models.CursoCard
import com.example.colmilloapp.Models.User
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_bottom_nav.*

class BottomNavActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    private var mDatabase: DatabaseReference? = null
    private var mMessageReference: DatabaseReference? = null
    private var usuario = User()

    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        lateinit var fragment: Fragment

        when(p0.itemId)
        {
            R.id.navigation_home -> {
                fragment = HomeActivity()
            }
            R.id.navigation_courses-> {
                fragment = Cursos()
            }
            R.id.navigation_profile-> {
                fragment = newInstance()
            }
            R.id.camera-> {
                fragment = Camera()
            }
        }
        return loadFragment(fragment)
    }
    private fun loadFragment(fragment: Fragment):Boolean{
        if(fragment!= null)
        {
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container,fragment).commit()
            return true
        }
        return false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_nav)
        var navigation: BottomNavigationView = findViewById(R.id.navigation)
        navigation.setOnNavigationItemSelectedListener(this)

        loadProfile()
    }

    fun newInstance(): UserProfileActivity {
        val f = UserProfileActivity()
       // val user = User("3", "pedro","www.caca.com", "CDMX", "Mexico", basura, basura)
        val bundle = Bundle()
        bundle.putParcelable("user", usuario)
        f.setArguments(bundle)
        return f
    }

    fun loadProfile(){

        var user=User()
        mDatabase = FirebaseDatabase.getInstance().reference
        mMessageReference = FirebaseDatabase.getInstance().getReference("Users")

        val messageListener = object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {


                Log.i("Usuarios",dataSnapshot.childrenCount.toString())

                dataSnapshot.children.forEach {
                    //Log.i("child-user",it.getValue(User::class.java).toString())
                    Log.i("que es it",it.toString())
                    var userTemp = it.getValue(User::class.java) as User
                    Log.i("userTemp",userTemp.toString())
                    if(userTemp.id == "1"){
                        Log.i("if","sirve el if id==1 putos")
                        usuario = userTemp
                    }

                }


                Log.i("Usuario","end if firebase")

            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Failed to read value
            }
        }

        mMessageReference!!.addValueEventListener(messageListener)



        Log.i("Curso","done firebase")


    }
}
