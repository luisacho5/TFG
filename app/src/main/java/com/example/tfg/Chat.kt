package com.example.tfg

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView

class Chat : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        val email: String? = intent.getStringExtra("email")
        val name:String?=intent.getStringExtra("name")

        val navigation: BottomNavigationView = findViewById(R.id.menu)
        navigation.setOnItemSelectedListener {item ->
            when(item.itemId){
                R.id.profile->{
                    val homeIntent = Intent(this,Profile::class.java)
                    homeIntent.putExtra("email",email)
                    homeIntent.putExtra("name",name)
                    startActivity(homeIntent)
                    true
                }
                R.id.chat->{
                    val homeIntent = Intent(this,Chat::class.java)
                    homeIntent.putExtra("email",email)
                    homeIntent.putExtra("name",name)
                    startActivity(homeIntent)
                    true
                }
                R.id.comunidad->{
                    val homeIntent = Intent(this,Community::class.java)
                    homeIntent.putExtra("email",email)
                    homeIntent.putExtra("name",name)
                    startActivity(homeIntent)
                    true
                }
                else->{
                    val homeIntent = Intent(this,Mapa::class.java)
                    homeIntent.putExtra("email",email)
                    homeIntent.putExtra("name",name)
                    startActivity(homeIntent)
                    true
                }
            }
        }

    }
}