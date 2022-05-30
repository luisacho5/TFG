package com.example.tfg

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import android.widget.TextView
import com.google.firebase.firestore.FirebaseFirestore

class ActivityCreateProfile : AppCompatActivity() {
    private val db= FirebaseFirestore.getInstance()
    private var email=""
    private var name=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_profile)
        val textViewName : TextView = findViewById(R.id.textViewName)
        email= intent.getStringExtra("email").toString()
        name= intent.getStringExtra("name").toString()
        textViewName.setText(name as String?)
        val button:Button=findViewById(R.id.submitButton)
        val bio : TextView = findViewById(R.id.infoUser)
        val facebook : TextView = findViewById(R.id.facebookUser)
        val twitter : TextView = findViewById(R.id.twitterUser)
        val spotify : TextView = findViewById(R.id.spotifyUser)
        val youtube : TextView = findViewById(R.id.youtubeUser)


        db.collection("users").document(email).get().addOnSuccessListener {
            textViewName.setText(it.get("name") as String?)
            spotify.setText(it.get("spotify") as String?)
            twitter.setText(it.get("twitter") as String?)
            youtube.setText(it.get("youtube") as String?)
            facebook.setText(it.get("facebook") as String?)
            bio.setText(it.get("biografia")as String?)
        }

        button.setOnClickListener {
            val yttxt=youtube.text.toString()
            val spotifytxt=spotify.text.toString()
            val twtrtxt= twitter.text.toString()
            val fbtxt=facebook.text.toString()
            val biotxt= bio.text.toString()
            val productor:RadioButton = findViewById(R.id.productor)
            val musico:RadioButton = findViewById(R.id.musico)
            val conciertos:RadioButton = findViewById(R.id.salaConcierto)
            val rol = if (productor.isChecked){
                "productor"
            } else if(musico.isChecked)
                "musico"
            else if(conciertos.isChecked)
                "concierto"
            else{
                "ensayo"
            }
              db.collection("users").document(email).set(
                  hashMapOf(
                      "name" to textViewName.text.toString(),
                      "rol" to rol,
                      "biografia" to biotxt,
                      "facebook" to fbtxt,
                      "twitter" to twtrtxt,
                      "spotify" to spotifytxt,
                      "youtube" to yttxt)
              )
            val homeIntent = Intent(this,Profile::class.java)
            homeIntent.putExtra("email",email)
            homeIntent.putExtra("name",textViewName.text.toString())
            startActivity(homeIntent)
        }
    }
}