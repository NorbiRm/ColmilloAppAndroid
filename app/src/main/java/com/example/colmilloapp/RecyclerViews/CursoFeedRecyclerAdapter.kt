package com.example.colmilloapp.RecyclerViews;

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.colmilloapp.Models.Foto
import com.example.colmilloapp.R
import android.view.Window.FEATURE_NO_TITLE
import android.app.Dialog
import android.media.Image
import android.view.Window




class CursoFeedRecyclerAdapter(private var context: Context, private val feedFotos: List<Foto?>): RecyclerView.Adapter<CursoFeedRecyclerAdapter.CursoFeedViewHolder>(){
        internal var options: RequestOptions = RequestOptions().centerCrop().placeholder(R.drawable.load_card).error(R.drawable.load_card)

        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): CursoFeedViewHolder {
        context = p0.context
        val view = LayoutInflater.from(p0.context).inflate(com.example.colmilloapp.R.layout.profile_foto, p0, false)


        val CursoFeedViewHolder = CursoFeedViewHolder(view)

        return CursoFeedViewHolder

        }

        override fun onBindViewHolder(p0: CursoFeedViewHolder, p1: Int) {
        Glide.with(context).load(feedFotos[p1]!!.imageURL).apply(options).into(p0.image)
        }

        override fun getItemCount(): Int {
        return feedFotos.size
        }

        class CursoFeedViewHolder(item : View) : RecyclerView.ViewHolder(item){
                internal var image = item.findViewById<ImageView>(R.id.imageView3)
                init {
                    image = item.findViewById(R.id.imageView3)
                }
        }
}
