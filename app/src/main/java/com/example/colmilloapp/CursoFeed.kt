package com.example.colmilloapp

import android.content.Context
import android.media.Image
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.colmilloapp.Models.CursoCard
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            curso = it.getSerializable(CURSO_PARAM) as CursoCard
        }
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


        name = view.findViewById(R.id.nombreImageFeed)
        image = view.findViewById(R.id.cursoImageFeed)

        val options = RequestOptions().centerCrop().placeholder(R.drawable.load_card).error(R.drawable.load_card)

        name!!.setText(cursoCard!!.nombre)

        Glide.with(this).load(cursoCard!!.image).apply(options).into(image!!)


    }
}
