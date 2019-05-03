package com.example.colmilloapp.Models

import android.annotation.SuppressLint
import android.media.Image
import android.support.design.internal.ParcelableSparseArray
import java.io.Serializable
import java.util.*
import kotlin.collections.ArrayList

@SuppressLint("RestrictedApi")
class User: Serializable, ParcelableSparseArray {
    var id:String=""
    var nombre:String = ""
    var imageProfile:String? = null
    var ciudad: String = ""
    var pais: String = ""
    var followers: List<String> = ArrayList<String>()
    var following: List<String> = ArrayList<String>()

    constructor(){
    }

    constructor(id: String,nombre: String,imageProfile: String, ciudad: String, pais: String, followers: ArrayList<String>, following: ArrayList<String>){
        this.id = id
        this.nombre = nombre
        this.imageProfile = imageProfile
        this.ciudad = ciudad
        this.pais = pais
        this.followers = followers
        this.following = following
    }

    override fun toString(): String {
        return "$id $nombre $ciudad $pais ${toStringFollowers()} ${toStringFollowing()}"
    }

    private fun toStringFollowers(): String{
        var followers: String =""
        this.followers.forEach {
            followers += it + ","
        }
        return followers

    }

    private fun toStringFollowing(): String{
        var following: String = ""
        this.following.forEach {
            following += it + ","
        }
        return following
    }
}