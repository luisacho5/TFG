package com.example.tfg

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.media.audiofx.Equalizer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.tfg.databinding.ActivityMapaBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.bottomnavigation.BottomNavigationView

class Mapa : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapaBinding
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        val email: String? = intent.getStringExtra("email")
        val name:String?=intent.getStringExtra("name")

        val navigation: BottomNavigationView = findViewById(R.id.menu)
        navigation.setOnItemSelectedListener {item ->
            when(item.itemId){
                R.id.profile->{
                    val homeIntent = Intent(this,Profile::class.java)
                    homeIntent.putExtra("email",email)
                    homeIntent.putExtra("name",name)
                    startActivity(homeIntent)
                    true
                }
                R.id.chat->{
                    val homeIntent = Intent(this,Chat::class.java)
                    homeIntent.putExtra("email",email)
                    homeIntent.putExtra("name",name)
                    startActivity(homeIntent)
                    true
                }
                R.id.comunidad->{
                    val homeIntent = Intent(this,Chat::class.java)
                    homeIntent.putExtra("email",email)
                    homeIntent.putExtra("name",name)
                    startActivity(homeIntent)
                    true
                }
                else->{
                    val homeIntent = Intent(this,Mapa::class.java)
                    homeIntent.putExtra("email",email)
                    homeIntent.putExtra("name",name)
                    startActivity(homeIntent)
                    true
                }
            }
        }
        fusedLocationProviderClient=LocationServices.getFusedLocationProviderClient(this)

    }
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        // Add a marker in Madrid
        val aux=getCurrentLocation()
        val sydney = LatLng(aux.latitud, aux.longitud)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Location"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }


        private data class Aux(val latitud:Double,val longitud:Double)

        @SuppressLint("MissingPermission")
        private fun getCurrentLocation():Aux{
            var latitud:Double=0.0
            var longitud:Double=0.0
            if(checkPermissions())
            {
                if(isLocationEnabled()){
                    fusedLocationProviderClient.lastLocation.addOnCompleteListener(this){task ->
                        val locationaux: Location? =task.result
                        if (locationaux==null){
                            Toast.makeText(applicationContext,"Null received",Toast.LENGTH_SHORT).show()
                        }else {
                            latitud = locationaux.latitude
                            longitud = locationaux.longitude
                        }
                    }
                }else{
                    Toast.makeText(applicationContext,"Turn on location",Toast.LENGTH_SHORT).show()
                    val intent =Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                    startActivity(intent)
                }
            }else{
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION),PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION)
            }
            return Aux(latitud,longitud)
        }
        private fun isLocationEnabled():Boolean{
            val locationManager:LocationManager=getSystemService(Context.LOCATION_SERVICE) as LocationManager
            return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        }

        companion object{
            private const val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION=100
        }
        private fun checkPermissions():Boolean{
            return (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)==PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED)
        }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION){
            if(grantResults.isNotEmpty() && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Toast.makeText(applicationContext,"Granted",Toast.LENGTH_SHORT).show()
                getCurrentLocation()
            }
            else{
                Toast.makeText(applicationContext,"DENIED",Toast.LENGTH_SHORT).show()
            }
        }
    }

}