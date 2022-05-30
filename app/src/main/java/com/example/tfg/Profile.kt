package com.example.tfg

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
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
        val facebookImg: ImageView = findViewById(R.id.imageFacebook)
        val spotifyImg: ImageView = findViewById(R.id.imageView7)
        val youtubeImg: ImageView = findViewById(R.id.imageView8)
        val twitterImg: ImageView = findViewById(R.id.imageEmail)
        val email: String? = intent.getStringExtra("email")
        val name: String? = intent.getStringExtra("name")
        val flag: Boolean? = intent.getBooleanExtra("flag",false)

        if (email != null) {
            db.collection("users").document(email).get().addOnSuccessListener {
                textViewName.setText(it.get("name") as String?)

                val facebook:Uri  = Uri.parse(it.get("facebook") as String?)
                val i = Intent(Intent.ACTION_VIEW,facebook)
                facebookImg.setOnClickListener{
                    startActivity(i)
                }

                val spotify:Uri  = Uri.parse(it.get("spotify") as String?)
                val i2 = Intent(Intent.ACTION_VIEW,spotify)
                spotifyImg.setOnClickListener{
                    startActivity(i2)
                }

                val twitter:Uri  = Uri.parse(it.get("twitter") as String?)
                val i3 = Intent(Intent.ACTION_VIEW,twitter)
                twitterImg.setOnClickListener{
                    startActivity(i3)
                }

                val yt:Uri  = Uri.parse(it.get("youtube") as String?)
                val i4= Intent(Intent.ACTION_VIEW,yt)
                youtubeImg.setOnClickListener{
                    startActivity(i4)
                }

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
                    val homeIntent = Intent(this,Community::class.java)
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

        val editImg: ImageView =findViewById(R.id.editImage)
        if(flag == true){
            editImg.visibility= View.INVISIBLE
        }
        editImg.setOnClickListener{
            val homeIntent = Intent(this,ActivityCreateProfile::class.java)
            homeIntent.putExtra("email",email)
            homeIntent.putExtra("name",name)
            startActivity(homeIntent)
        }
    }
}
