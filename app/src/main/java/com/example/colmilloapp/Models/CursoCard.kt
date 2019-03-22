package com.example.colmilloapp.Models

import android.media.Image
import java.io.Serializable
import java.util.*
import com.google.firebase.database.IgnoreExtraProperties


@IgnoreExtraProperties
class CursoCard: Serializable {
    var id:String=""
    var nombre:String = ""
    var fechaInicio:String = ""
    var fechaFin:String = ""
    var cupo:Int = 0
    var image:String? = null

    constructor(){
    }

    constructor(id:String,nombre:String,fechaInicio:String,fechaFin:String,cupo:Int,image:String){
        this.id = id
        this.nombre = nombre
        this.fechaInicio = fechaInicio
        this.fechaFin = fechaFin
        this.cupo = cupo
        this.image = image
    }

    override fun toString(): String {
        return "$id $nombre $fechaInicio $fechaFin $cupo $image"
    }
}
