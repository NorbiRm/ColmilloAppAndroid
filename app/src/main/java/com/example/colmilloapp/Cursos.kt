package com.example.colmilloapp

import android.itesm.edu.Curso.adapters.CursosRecyclerAdapter
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.colmilloapp.Models.CursoCard
import org.json.JSONException
import org.json.JSONObject
import java.util.ArrayList

class Cursos : AppCompatActivity() {

    private val JSON_URL = "https://api.pokemontcg.io/v1/cards"

    private var request: JsonObjectRequest? = null
    private var requestQueue: RequestQueue? = null
    private var cards: MutableList<CursoCard>? = null
    private var recyclerView: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        cards = ArrayList<CursoCard>()
        recyclerView = findViewById(R.id.recyclerView)
        jsonrequest()

    }

    private fun jsonrequest() {
        request = JsonObjectRequest(Request.Method.GET, JSON_URL, null,
            Response.Listener { response ->
                try {
                    val cardsJson = response.getJSONArray("cards")
                    var jsonObject: JSONObject? = null

                    for (i in 0 until cardsJson.length()) {

                        jsonObject = cardsJson.getJSONObject(i)
                        val cursoCard = CursoCard()
                        cursoCard.id = (jsonObject!!.getString("id"))
                        cursoCard.nombre = (jsonObject.getString("name"))
                        cursoCard.image = (jsonObject.getString("imageUrl"))
//                        pokeCard.setArtist(jsonObject.getString("artist"))

                        cards!!.add(cursoCard)
                    }
                } catch (jsonException: JSONException) {
                    jsonException.printStackTrace()
                }

                setRecyclerView(cards!!)
            },
            Response.ErrorListener {
                Toast.makeText(applicationContext, "Error de server", Toast.LENGTH_LONG)
                    .show()
            })

        requestQueue = Volley.newRequestQueue(this@Cursos)
        requestQueue!!.add(request!!)
    }

    private fun setRecyclerView(cursoCards: List<CursoCard>) {
        val cursosRecyclerAdapter = CursosRecyclerAdapter(this, cursoCards)
        recyclerView!!.layoutManager = LinearLayoutManager(this)
        recyclerView!!.adapter = cursosRecyclerAdapter


    }


}
