package com.example.tfg

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.FirebaseFirestore

class Profile : AppCompatActivity() {
    private val db= FirebaseFirestore.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
       val textViewName : TextView = findViewById(R.id.textViewName)
        val textViewRol: TextView=findViewById(R.id.textViewRol)
        val textViewBio: TextView=findViewById(R.id.textViewBio)
        val email: String? = intent.getStringExtra("email")

        if (email != null) {
            db.collection("users").document(email).get().addOnSuccessListener {
                textViewName.setText(it.get("name") as String?)
                textViewRol.setText(it.get("rol") as String?)
                textViewBio.setText(it.get("biografia")as String?)
            }
        }
        val navigation: BottomNavigationView = findViewById(R.id.menu)
        navigation.setOnItemSelectedListener {item ->
            when(item.itemId){
                R.id.profile->{
                    val homeIntent = Intent(this,Profile::class.java)
                    homeIntent.putExtra("email",email)
                    homeIntent.putExtra("name",textViewName.text)
                    startActivity(homeIntent)
                    true
                }
                R.id.chat->{
                    val homeIntent = Intent(this,Chat::class.java)
                    homeIntent.putExtra("email",email)
                    homeIntent.putExtra("name",textViewName.text)
                    startActivity(homeIntent)
                    true
                }
                R.id.comunidad->{
                    val homeIntent = Intent(this,Chat::class.java)
                    homeIntent.putExtra("email",email)
                    homeIntent.putExtra("name",textViewName.text)
                    startActivity(homeIntent)
                    true
                }
                else->{
                    val homeIntent = Intent(this,Mapa::class.java)
                    homeIntent.putExtra("email",email)
                    homeIntent.putExtra("name",textViewName.text)
                    startActivity(homeIntent)
                    true
                }
            }
        }
    }
}