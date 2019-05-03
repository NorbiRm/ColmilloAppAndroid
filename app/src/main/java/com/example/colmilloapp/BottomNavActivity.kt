package com.example.colmilloapp

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.support.v7.widget.RecyclerView
import com.example.colmilloapp.Models.User
import kotlinx.android.synthetic.main.activity_bottom_nav.*

class BottomNavActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        lateinit var fragment: Fragment

        when(p0.itemId)
        {
            R.id.navigation_home -> fragment = HomeActivity()
            R.id.navigation_courses-> {
                fragment = Cursos()
            }
            R.id.navigation_profile-> {
                var basura = ArrayList<String>()
                basura.add("Norbi es Puto")
                val user = User("3", "pedro","www.caca.com", "CDMX", "Mexico", basura, basura)
                val bundle = Bundle()
                bundle.putParcelable("user", user)
                fragment = UserProfileActivity()
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
    }

}
