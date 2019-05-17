package com.example.colmilloapp

import android.content.Context
import android.media.Image
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.colmilloapp.Models.CursoCard
import com.example.colmilloapp.Models.Foto
import com.example.colmilloapp.RecyclerViews.CursoFeedRecyclerAdapter
import com.example.colmilloapp.RecyclerViews.UserFotosAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_curso_feed.*
import java.io.Serializable


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val CURSO_PARAM = "curso"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [CursoFeed.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [CursoFeed.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class CursoFeed : Fragment() {
    // TODO: Rename and change types of parameters
    private var curso: CursoCard? = null
    private var listener: OnFragmentInteractionListener? = null

    private var name: TextView? = null
    private var image: ImageView? = null

    private var cursoFotos:MutableList<Foto?>?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            curso = it.getSerializable(CURSO_PARAM) as CursoCard
        }
        cursoFotos = ArrayList<Foto?>()
        Log.i("CursoFeed",curso!!.fotos.toString())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_curso_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadCurso(view,curso);


    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }


    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CursoFeed.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(curso: CursoCard) =
            CursoFeed().apply {
                arguments = Bundle().apply {
                    putSerializable(CURSO_PARAM, curso as Serializable)
                }
            }
    }

    //---------------------------------------------------------------------------------------------------------------

    private fun loadCurso(view:View,cursoCard: CursoCard?) {

        jsonRequest(cursoCard)

        name = view.findViewById(R.id.nombreImageFeed)
        image = view.findViewById(R.id.cursoImageFeed)

        val options = RequestOptions().centerCrop().placeholder(R.drawable.load_card).error(R.drawable.load_card)

        name!!.setText(cursoCard!!.nombre)

        Glide.with(this).load(cursoCard!!.image).apply(options).into(image!!)


    }
    private fun jsonRequest(cursoCard: CursoCard?){
        var mReference = FirebaseDatabase.getInstance().getReference("Fotos")

        if(curso!!.fotos!=null){
            mReference!!.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapShot: DataSnapshot) {
                    Log.i("CursoFeed","snapshot"+dataSnapShot.childrenCount.toString())

                    dataSnapShot.children.forEach {
                        var fotoTemp = it.getValue(Foto::class.java) as Foto
                        for(f in curso!!.fotos!!) {
                            if (fotoTemp.idFoto == f.value) {
                                cursoFotos!!.add(fotoTemp)
                            }
                        }
                    }
                    Log.i("CursoFeed","Fotos" +cursoFotos!!.toString())
                    setRecycleView(cursoFotos!!)
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // ...
                }
            })
        }
    }

    private fun setRecycleView(cursoFotos:MutableList<Foto?>){
        val fotoRecyclerAdapter = CursoFeedRecyclerAdapter(BottomNavActivity(), cursoFotos)
        recyclerCursoFotos!!.layoutManager = GridLayoutManager(BottomNavActivity(),4)
        recyclerCursoFotos!!.adapter = fotoRecyclerAdapter

    }
}
