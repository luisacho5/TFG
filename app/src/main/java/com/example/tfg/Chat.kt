package com.example.tfg

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView

class Chat : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        val navigation: BottomNavigationView = findViewById(R.id.menu)
        navigation.setOnItemSelectedListener {item ->
            when(item.itemId){
                R.id.profile->{
                    val homeIntent = Intent(this,Profile::class.java)
                    startActivity(homeIntent)
                    true
                }
                R.id.chat->{
                    val homeIntent = Intent(this,Chat::class.java)
                    startActivity(homeIntent)
                    true
                }
                R.id.comunidad->{
                    val homeIntent = Intent(this,Community::class.java)
                    startActivity(homeIntent)
                    true
                }
                else->{
                    val homeIntent = Intent(this,Mapa::class.java)
                    startActivity(homeIntent)
                    true
                }
            }
        }

    }
}