package com.example.tfg


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }




    fun signup(view: View) {
        val emailEditText:EditText = findViewById(R.id.emailEditText)
        val passwordEditText:EditText = findViewById(R.id.passwordEditText)

        if(emailEditText.text.isNotEmpty() && passwordEditText.text.isNotEmpty()){
            FirebaseAuth.getInstance()
                .createUserWithEmailAndPassword(emailEditText.text.toString(),
                    passwordEditText.text.toString()).addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val homeIntent = Intent(this,Chat::class.java)
                        startActivity(homeIntent)
                    }
                    else {
                        val builder = AlertDialog.Builder(this)
                        builder.setTitle("Error")
                        builder.setMessage("Se ha producido un error de autenticando")
                        builder.setPositiveButton("Aceptar",null)
                        val dialog: AlertDialog=builder.create()
                        dialog.show()
                    }
                }
        }
    }

    fun login(view: View) {
        val emailEditText:EditText = findViewById(R.id.emailEditText)
        val passwordEditText:EditText = findViewById(R.id.passwordEditText)
        if(emailEditText.text.isNotEmpty() && passwordEditText.text.isNotEmpty()){

            FirebaseAuth.getInstance().signInWithEmailAndPassword(emailEditText.text.toString(),
                passwordEditText.text.toString()).addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val homeIntent = Intent(this,Chat::class.java)
                    startActivity(homeIntent)
                }
                else {
                    val builder = AlertDialog.Builder(this)
                    builder.setTitle("Error")
                    builder.setMessage("Se ha producido un error de autenticando")
                    builder.setPositiveButton("Aceptar",null)
                    val dialog: AlertDialog=builder.create()
                    dialog.show()
                }
            }
        }
    }

}