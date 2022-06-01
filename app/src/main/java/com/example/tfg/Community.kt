package com.example.tfg

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.*
import com.google.firebase.ktx.Firebase

class Community : AppCompatActivity() {
    private val db= FirebaseFirestore.getInstance()
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MyAdapter
    private lateinit var users: ArrayList<User>
    private val user= Firebase.auth.currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_community)
        val email: String? = user!!.email
        val name:String?=user.displayName
        recyclerView= findViewById(R.id.lista_users)
        users= arrayListOf()
        adapter= MyAdapter(users)
        recyclerView.layoutManager=LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter=adapter

        adapter.setOnItemClickListener(object : MyAdapter.OnItemClickListener{
            override fun onItemClick(position: Int) {
                val homeIntent = Intent(this@Community, ShowProfile::class.java)
                homeIntent.putExtra("email", users.get(position).email)
                homeIntent.putExtra("name", users.get(position).name)
                startActivity(homeIntent)
            }

        })
        initialize()

        val navigation: BottomNavigationView = findViewById(R.id.menu)
        navigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.profile -> {
                    val homeIntent = Intent(this, Profile::class.java)
                    startActivity(homeIntent)
                    true
                }
                R.id.chat -> {
                    val homeIntent = Intent(this, Chat::class.java)
                    homeIntent.putExtra("email", email)
                    homeIntent.putExtra("name", name)
                    startActivity(homeIntent)
                    true
                }
                R.id.comunidad -> {
                    val homeIntent = Intent(this, Community::class.java)
                    startActivity(homeIntent)
                    true
                }
                else -> {
                    val homeIntent = Intent(this, Mapa::class.java)
                    startActivity(homeIntent)
                    true
                }
            }

        }

    }

    private fun initialize() {
        db.collection("users")
            .addSnapshotListener(object: EventListener<QuerySnapshot>{
                override fun onEvent(
                    value: QuerySnapshot?,
                    error: FirebaseFirestoreException?
                ) {
                    if(error !=null){
                        Log.e("Firestore error", error.message.toString())
                        return
                    }
                    for (dc : DocumentChange in value?.documentChanges!!){
                        if(dc.type == DocumentChange.Type.ADDED){
                            users.add(dc.document.toObject(User::class.java))
                        }
                    }
                    adapter.notifyDataSetChanged()
                }
            } )
    }
}