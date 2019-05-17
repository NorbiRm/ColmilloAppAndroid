package com.example.colmilloapp

import android.net.Uri
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.colmilloapp.Models.CursoCard


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class CursosCardActivity : Fragment(), BottomNavigationView.OnNavigationItemSelectedListener{
    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private var param1: String? = null
    private var param2: String? = null
    private var listener: CursosCardActivity.OnFragmentInteractionListener? = null

    var curso:CursoCard?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("CursoFragment","onCreate()")
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
            curso = it.getSerializable("curso") as CursoCard
        }
        Log.i("CursoFragment",curso.toString())

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        Log.i("CursoFragment","OnCreatView()")
        return inflater.inflate(R.layout.activity_cursos_card, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i("CursoFragment","OnViewCreated()")

        //loadCurso(view,curso!!)
    }
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }
    private fun loadCurso(view:View,cursoCard: CursoCard) {

        val name: TextView

        val image: ImageView
        name = view.findViewById(R.id.nombreImageFeed)
        image = view.findViewById(R.id.cursoImageFeed)

        val options = RequestOptions().centerCrop().placeholder(R.drawable.load_card).error(R.drawable.load_card)

        name.setText(cursoCard.nombre)

        Glide.with(this).load(cursoCard.image).apply(options).into(image)


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
            CursosCardActivity().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                    putSerializable("curso",curso)
                }
            }
    }

}
