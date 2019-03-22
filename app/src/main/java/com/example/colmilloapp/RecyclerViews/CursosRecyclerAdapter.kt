package android.itesm.edu.Curso.adapters

import android.content.Context
import android.content.Intent


import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast



import com.bumptech.glide.Glide
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.request.RequestOptions
import com.example.colmilloapp.CursosCardActivity
import com.example.colmilloapp.R
import com.firebase.ui.storage.images.FirebaseImageLoader;


import com.example.colmilloapp.Models.CursoCard
import com.google.firebase.storage.FirebaseStorage



class CursosRecyclerAdapter(private val context: Context, private val cards: List<CursoCard?>) :
    RecyclerView.Adapter<CursosRecyclerAdapter.CursoRecordHolder>() {
    internal var options: RequestOptions

    init {
        options = RequestOptions().centerCrop().placeholder(R.drawable.load_card).error(R.drawable.load_card)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): CursoRecordHolder {
        val view: View
        val inflater = LayoutInflater.from(context)
        view = inflater.inflate(R.layout.activity_cursos_card, viewGroup, false)

        val CursoRecordHolder = CursoRecordHolder(view)

        CursoRecordHolder.itemView.setOnClickListener {
            val CursoCard = cards[CursoRecordHolder.adapterPosition]
            Toast.makeText(context, CursoCard!!.nombre, Toast.LENGTH_SHORT).show()
            val it = Intent(context, CursosCardActivity::class.java)
            it.putExtra("CursoCard", CursoCard)

            context.startActivity(it)
        }

        return CursoRecordHolder
    }

    override fun onBindViewHolder(CursoRecordHolder: CursoRecordHolder, i: Int) {

        CursoRecordHolder.nombre.setText(cards[i]!!.nombre)

        Glide.with(context).load(cards[i]!!.image).apply(options).into(CursoRecordHolder.image);
    }

    override fun getItemCount(): Int {
        return cards.size
    }

    class CursoRecordHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        internal var nombre: TextView
        internal var image: ImageView

        init {
            nombre = itemView.findViewById(R.id.nombre)
            image = itemView.findViewById(R.id.image)
        }
    }

}
