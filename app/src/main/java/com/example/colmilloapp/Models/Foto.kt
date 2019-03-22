package com.example.colmilloapp.Models

import android.media.Image
import java.io.Serializable
import java.util.*
import kotlin.collections.ArrayList

class Foto(): Serializable {
    var idFoto:String=""
    var idUser:String=""
    var imageURL:String? = null
    var descripcion:String? = null
    var likes:Int = 0

    override fun toString(): String {
        return "$idFoto/$idUser/$imageURL/ $likes/ $descripcion"
    }
}
