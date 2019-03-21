package com.example.colmilloapp.Models

import android.media.Image
import java.io.Serializable
import java.util.*

class CursoCard(): Serializable {
    var id:String=""
    var nombre:String = ""
    var fechaInicio:Date = Date()
    var fechaFin:Date = Date()
    var cupo:Int = 0
    var image:String? = null
}