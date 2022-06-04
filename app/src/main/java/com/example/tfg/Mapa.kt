package com.example.tfg

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.tfg.databinding.ActivityMapaBinding
import com.google.android.gms.maps.model.Marker
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.*
import com.google.firebase.ktx.Firebase

class Mapa : AppCompatActivity(),
    GoogleMap.OnInfoWindowClickListener,OnMapReadyCallback,GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnMyLocationClickListener {

    private lateinit var map: GoogleMap
    private lateinit var binding: ActivityMapaBinding
    private val db= FirebaseFirestore.getInstance()
    private val user= Firebase.auth.currentUser
    private var email:String? = null
    private var name: String? = null
    private var latitude:Double = 0.0
    private var longitude:Double = 0.0

    companion object{
        const val LOCATION_REQUEST_CODE = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        email = user!!.email
        name = user.displayName
        createFragment()
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

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        map.setOnMyLocationButtonClickListener(this)
        map.setOnMyLocationClickListener(this)
        enableLocation()
        initialize()
        map.setOnInfoWindowClickListener(this)

    }

    private fun createFragment() {
        val mapFragment: SupportMapFragment =
            supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }


    private fun createMarker(coordenadas:LatLng,nombre:String,user:User) {
        val marker = MarkerOptions().position(coordenadas).title(nombre)
        val mark = map.addMarker(marker)
        if (mark != null) {
            mark.tag=user
        }

    }

    private fun isLocationPermissionGranted() =
        ContextCompat.checkSelfPermission(this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

    @SuppressLint("MissingPermission")
    private fun enableLocation(){
        if(!::map.isInitialized) return
        if(isLocationPermissionGranted()){
            map.isMyLocationEnabled= true
            val lm = getSystemService(LOCATION_SERVICE) as LocationManager
            val location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            longitude = location!!.longitude
            latitude = location!!.latitude
            email?.let {
                db.collection("users").document(it)
                    .update("latitud",latitude)
                db.collection("users").document(it)
                    .update("longitud", longitude)
            }
            }else{
            askPermissions()
        }
    }

    private fun askPermissions(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_FINE_LOCATION)){
            Toast.makeText(this, "Ve a ajustes y acepta permisos ",Toast.LENGTH_SHORT).show()
        }else{
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),LOCATION_REQUEST_CODE)
        }
    }

    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            LOCATION_REQUEST_CODE -> if(grantResults.isNotEmpty() && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                map.isMyLocationEnabled = true
            }else{
                Toast.makeText(this, "Para activar la localización ve a ajustes y acepta los permisos", Toast.LENGTH_SHORT).show()
            }
            else -> {}
        }
    }

    @SuppressLint("MissingPermission")
    override fun onResumeFragments() {
        super.onResumeFragments()
        if (!::map.isInitialized) return
        if(!isLocationPermissionGranted()){
            map.isMyLocationEnabled = false
            Toast.makeText(this, "Para activar la localización ve a ajustes y acepta los permisos", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onMyLocationButtonClick(): Boolean {
        email?.let {
            db.collection("users").document(it)
                .update("latitud",latitude)
            db.collection("users").document(it)
                .update("longitud", longitude)
        }
        return false
    }

    override fun onMyLocationClick(p0: Location) {
        email?.let {
            db.collection("users").document(it)
                .update("latitud",p0.latitude)
            db.collection("users").document(it)
                .update("longitud", p0.longitude)
        }
    }

    private fun initialize() {
        db.collection("users")
            .addSnapshotListener(object: EventListener<QuerySnapshot> {
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
                           val user=dc.document.toObject(User::class.java)
                            if(!user.name.equals(name)) {
                                createMarker(LatLng(user.latitud, user.longitud), user.name,user)
                            }
                        }
                    }
                }
            } )
    }

    override fun onInfoWindowClick(p0: Marker) {
        val user:User= p0.tag as User
        val homeIntent = Intent(this, ShowProfile::class.java)
        homeIntent.putExtra("email", user.email)
        homeIntent.putExtra("name", user.name)
        startActivity(homeIntent)
    }

}
