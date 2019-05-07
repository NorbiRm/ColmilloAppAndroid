package com.example.colmilloapp.Models

import android.media.Image
import java.io.Serializable
import java.util.*
import kotlin.collections.ArrayList
import android.R.attr.author
import com.google.firebase.database.Exclude



class Foto(): Serializable {
    var idFoto:String=""
    var idUser:String=""
    var imageURL:String? = null
    var descripcion:String? = null
    var likes:Int = 0



    override fun toString(): String {
        return "$idFoto/$idUser/$imageURL/ $likes/ $descripcion"
    }
    @Exclude
    fun toMap(): Map<String, Any> {
        val result = HashMap<String,Any>()
        result.put("idFoto", idFoto)
        result.put("descripcion", descripcion!!)
        result.put("idUser", idUser)
        result.put("imageURL", imageURL!!)
        result.put("likes", likes)

        return result
    }

}
