package com.example.tfg

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import android.widget.TextView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class ActivityCreateProfile : AppCompatActivity() {
    private val db= FirebaseFirestore.getInstance()
    private var email=""
    private var name=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_profile)
        val nameView: TextView = findViewById(R.id.name)
        val button:Button=findViewById(R.id.submitButton)
        val bio : TextView = findViewById(R.id.infoUser)
        val facebook : TextView = findViewById(R.id.facebookUser)
        val twitter : TextView = findViewById(R.id.twitterUser)
        val spotify : TextView = findViewById(R.id.spotifyUser)
        val youtube : TextView = findViewById(R.id.youtubeUser)
        var latitud: Double? = 0.0
        var longitud: Double? =0.0
        val user = Firebase.auth.currentUser
        if (user != null) {
            user.email?.let {
                db.collection("users").document(it).get().addOnSuccessListener {
                    nameView.setText(it.get("name") as String?)
                    spotify.setText(it.get("spotify") as String?)
                    twitter.setText(it.get("twitter") as String?)
                    youtube.setText(it.get("youtube") as String?)
                    facebook.setText(it.get("facebook") as String?)
                    bio.setText(it.get("biografia")as String?)
                    latitud = it.get("latitud")as Double?
                    longitud = it.get("longitud")as Double?
                }
                if (latitud==null){
                    latitud= 0.0
                }
                if(longitud==null) {
                    longitud = 0.0
                }
            }
            email= user.email.toString()
        }

        button.setOnClickListener {
            name=nameView.text.toString()
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
                "sala"
            else{
                "ensayo"
            }
              db.collection("users").document(email).set(
                  hashMapOf(
                      "email" to email,
                      "name" to name,
                      "rol" to rol,
                      "biografia" to biotxt,
                      "facebook" to fbtxt,
                      "twitter" to twtrtxt,
                      "spotify" to spotifytxt,
                      "youtube" to yttxt,
                      "latitud" to latitud,
                      "longitud" to longitud,
                        "foto" to "https://loremflickr.com/320/240/rockmusic")
              )

            val profileUpdates = userProfileChangeRequest {
                displayName = name
            }
            user!!.updateProfile(profileUpdates)
            val homeIntent = Intent(this,Profile::class.java)
            startActivity(homeIntent)
        }
    }
}