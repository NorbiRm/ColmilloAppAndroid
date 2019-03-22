package com.example.colmilloapp.Models

import android.media.Image
import java.io.Serializable
import java.util.*
import kotlin.collections.ArrayList

class User(): Serializable {
    var id:String=""
    var nombre:String = ""
    var imageProfile:String? = null
    var ciudad: String = ""
    var pais: String = ""
    var followers: List<String> = ArrayList<String>()
    var following: List<String> = ArrayList<String>()


}