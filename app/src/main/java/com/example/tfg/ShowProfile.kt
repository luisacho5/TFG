package com.example.tfg

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
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
        val foto: ImageView = findViewById(R.id.foto)
        val email: String? = intent.getStringExtra("email")

        db.collection("users").document(email!!).get().addOnSuccessListener {
            textViewName.setText(it.get("name") as String?)

            val fb:String?=it.get("facebook") as String?
            if(!fb.equals("")){
                facebookImg.visibility= View.VISIBLE
                val facebook: Uri = Uri.parse(fb)
                val i = Intent(Intent.ACTION_VIEW, facebook)
                facebookImg.setOnClickListener {
                    startActivity(i)
                }
            }else{
                facebookImg.visibility= View.INVISIBLE
            }


            val spotify: String? = it.get("spotify") as String?
            if(!spotify.equals("")) {
                spotifyImg.visibility= View.VISIBLE
                val i2 = Intent(Intent.ACTION_VIEW, Uri.parse(spotify))
                spotifyImg.setOnClickListener {
                    startActivity(i2)
                }
            }else{
                spotifyImg.visibility= View.INVISIBLE
            }

            val twitter: String?= it.get("twitter") as String?
            if(!twitter.equals("")) {
                twitterImg.visibility= View.VISIBLE
                val i3 = Intent(Intent.ACTION_VIEW, Uri.parse(twitter))
                twitterImg.setOnClickListener {
                    startActivity(i3)
                }
            }
            else{
                twitterImg.visibility= View.INVISIBLE
            }

            val yt: String? = it.get("youtube") as String?
            if(!yt.equals("")) {
                youtubeImg.visibility= View.VISIBLE
                val i4 = Intent(Intent.ACTION_VIEW, Uri.parse(yt))
                youtubeImg.setOnClickListener {
                    startActivity(i4)
                }
            }else{
                youtubeImg.visibility= View.INVISIBLE
            }

            textViewRol.setText(it.get("rol") as String?)
            textViewBio.setText(it.get("biografia") as String?)
            Glide.with(this).load(it.get("foto") as String?).into(foto)
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