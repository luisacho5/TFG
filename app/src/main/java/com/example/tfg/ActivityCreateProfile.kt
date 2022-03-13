package com.example.tfg

import android.content.Intent
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
        button.setOnClickListener {
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
            val bio : TextView = findViewById(R.id.infoUser)
            val biotxt= bio.text.toString()
            val facebook : TextView = findViewById(R.id.facebookUser)
            val fbtxt=facebook.text.toString()
            val twitter : TextView = findViewById(R.id.twitterUser)
            val twtrtxt= twitter.text.toString()
            val spotify : TextView = findViewById(R.id.spotifyUser)
            val spotifytxt=spotify.text.toString()
            val youtube : TextView = findViewById(R.id.youtubeUser)
            val yttxt=youtube.text.toString()


              db.collection("users").document(email).set(
                  hashMapOf(
                      "name" to name,
                      "rol" to rol,
                      "biografia" to biotxt,
                      "facebook" to fbtxt,
                      "twitter" to twtrtxt,
                      "spotify" to spotifytxt,
                      "youtube" to yttxt)
              )
            val homeIntent = Intent(this,Profile::class.java)
            homeIntent.putExtra("email",email)
            homeIntent.putExtra("name",name)
            startActivity(homeIntent)
        }
    }
}