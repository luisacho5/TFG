package com.example.tfg

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import com.google.firebase.firestore.FirebaseFirestore

class Profile : AppCompatActivity() {
    private val db= FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
       val textViewName : TextView = findViewById(R.id.textViewName)
        val email: String? = intent.getStringExtra("email")

        if (email != null) {
            db.collection("users").document(email).get().addOnSuccessListener {
                textViewName.setText(it.get("name") as String?)
            }
        }
    }
}