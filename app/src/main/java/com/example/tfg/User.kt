package com.example.tfg

import android.net.Uri

data class User(val email:String,
                val name:String,
                val rol:String,
                val biografia:String,
                val facebook: String,
                val twitter:String,
                val spotify:String,
                val youtube:String,
                val latitud:Double,
                val longitud: Double,
                val foto:String){
    constructor() : this("","","","","","","","",0.0,0.0,"")
}
