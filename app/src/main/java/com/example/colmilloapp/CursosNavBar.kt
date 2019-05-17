package com.example.colmilloapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.view.MenuItem
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.example.colmilloapp.Models.CursoCard
import com.example.colmilloapp.Models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_bottom_nav.*
import android.R.attr.fragment
import java.io.Serializable


class CursosNavBar : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    var cursoCard:CursoCard?=null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cursos_nav_bar)

        var navigation: BottomNavigationView = findViewById(R.id.cursosnavigation)
        navigation.setOnNavigationItemSelectedListener(this)

        cursoCard = intent.getSerializableExtra("CursoCard") as CursoCard
        Log.i("CursoNavBar","Curso: "+cursoCard.toString())
        //loadProfile()
        //loadFragment(CursoCard())
    }

    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        lateinit var fragment: Fragment

        Log.i("BottomNavBar","Item selected:" + p0.itemId)

        when(p0.itemId)
        {
            R.id.cursoFragment -> {

                fragment = CursoFeed()
                val bundle = Bundle()
                bundle.putSerializable("curso", cursoCard as Serializable)
                fragment.arguments = bundle

                Log.i("CursoNavBar","Going to cursoFragment")

            }
            R.id.fotoCursoFragment-> {
                //fragment = Cursos()
            }
            else ->{
                //fragment = HomeActivity()
            }
        }
        return loadFragment(fragment)
    }
    private fun loadFragment(fragment: Fragment):Boolean{
        if(fragment!= null)
        {
            supportFragmentManager.beginTransaction().replace(R.id.curso_fragment_container,fragment).commit()
            return true
        }
        return false
    }
}