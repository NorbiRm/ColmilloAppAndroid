package com.example.colmilloapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.colmilloapp.Models.User
import kotlinx.android.synthetic.main.activity_user_profile.*

class UserProfileActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)
        val user_profile = intent.getSerializableExtra("profile") as User
        loadProfile(user_profile)
    }

    fun loadProfile(user_profile: User){
        val image_portada : ImageView = findViewById(R.id.imagePortada)
        val nombreUser : TextView = findViewById(R.id.nombreUser)
        val followersUser : TextView = findViewById(R.id.followersUser)
        val following : TextView = findViewById(R.id.followingUser)

        val options = RequestOptions().centerCrop().placeholder(R.drawable.load_card).error(R.drawable.load_card)

        Glide.with(this).load(user_profile.imageProfile).apply(options).into(image_portada)
        nombreUser.setText(user_profile.nombre)
        followersUser.setText(user_profile.followers.size)
        followingUser.setText(user_profile.following.size)



    }
}