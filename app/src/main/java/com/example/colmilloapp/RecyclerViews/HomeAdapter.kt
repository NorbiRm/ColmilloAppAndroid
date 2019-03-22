package com.example.colmilloapp.RecyclerViews
import android.app.DownloadManager
import android.content.Context
import android.os.Handler
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
import org.w3c.dom.Text


class HomeAdapter(private var context: Context, private val feedFotos: List<Foto?>):

    RecyclerView.Adapter<HomeAdapter.UserFotoViewHolder>(){
    internal var options: RequestOptions = RequestOptions().centerCrop().placeholder(R.drawable.load_card).error(R.drawable.load_card)

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): UserFotoViewHolder {
        context = p0.context
        val view =  LayoutInflater.from(p0.context).inflate(R.layout.feed_foto, p0, false)
        return UserFotoViewHolder(view)
    }

    override fun getItemCount(): Int {
        return feedFotos.size
    }

    override fun onBindViewHolder(p0: UserFotoViewHolder, p1: Int) {
        p0.user.setText(feedFotos[p1]!!.idUser)
        p0.likes.setText(feedFotos[p1]!!.likes.toString())
        p0.descripcion.setText(feedFotos[p1]!!.descripcion.toString())
        Glide.with(context).load(feedFotos[p1]!!.imageURL).apply(options).into(p0.image)
    }

    inner class UserFotoViewHolder(item : View) : RecyclerView.ViewHolder(item){
        internal var image = item.findViewById<ImageView>(R.id.imageView2)
        internal var user = item.findViewById<TextView>(R.id.usuario)
        internal var likes = item.findViewById<TextView>(R.id.likes)
        internal var descripcion = item.findViewById<TextView>(R.id.descripcion)
    }

}