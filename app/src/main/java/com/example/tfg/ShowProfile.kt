package com.example.tfg

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class ShowProfile : AppCompatActivity() {
    private val db= FirebaseFirestore.getInstance()
    private val user= Firebase.auth.currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_profile)
        val textViewName : TextView = findViewById(R.id.textViewName)
        val textViewRol: TextView =findViewById(R.id.textViewRol)
        val textViewBio: TextView =findViewById(R.id.textViewBio)
        val facebookImg: ImageView = findViewById(R.id.imageFacebook)
        val spotifyImg: ImageView = findViewById(R.id.imageView7)
        val youtubeImg: ImageView = findViewById(R.id.imageView8)
        val twitterImg: ImageView = findViewById(R.id.imageEmail)
        val editImg: ImageView = findViewById(R.id.editImage)
        val email: String? = intent.getStringExtra("email")

        db.collection("users").document(email!!).get().addOnSuccessListener {
            textViewName.setText(it.get("name") as String?)

            val facebook: Uri = Uri.parse(it.get("facebook") as String?)
            val i = Intent(Intent.ACTION_VIEW, facebook)
            facebookImg.setOnClickListener {
                startActivity(i)
            }

            val spotify: Uri = Uri.parse(it.get("spotify") as String?)
            val i2 = Intent(Intent.ACTION_VIEW, spotify)
            spotifyImg.setOnClickListener {
                startActivity(i2)
            }

            val twitter: Uri = Uri.parse(it.get("twitter") as String?)
            val i3 = Intent(Intent.ACTION_VIEW, twitter)
            twitterImg.setOnClickListener {
                startActivity(i3)
            }

            val yt: Uri = Uri.parse(it.get("youtube") as String?)
            val i4 = Intent(Intent.ACTION_VIEW, yt)
            youtubeImg.setOnClickListener {
                startActivity(i4)
            }

            textViewRol.setText(it.get("rol") as String?)
            textViewBio.setText(it.get("biografia") as String?)
        }

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
                    homeIntent.putExtra("email",user!!.email)
                    homeIntent.putExtra("name",textViewName.text)
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