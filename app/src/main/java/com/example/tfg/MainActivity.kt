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
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity() {
    private val db= FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }


    fun signup(view: View) {
        val emailEditText:EditText = findViewById(R.id.emailEditText)
        val passwordEditText:EditText = findViewById(R.id.passwordEditText)
        val nameEditText:EditText=findViewById(R.id.nombreEditTextView)
        val name:String=nameEditText.text.toString()
        if(emailEditText.text.isNotEmpty() && passwordEditText.text.isNotEmpty()){
            FirebaseAuth.getInstance()
                .createUserWithEmailAndPassword(emailEditText.text.toString(),
                    passwordEditText.text.toString()).addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        db.collection("users").document(emailEditText.text.toString()).set(
                            hashMapOf("name" to name)
                        )

                        val homeIntent = Intent(this,Profile::class.java)
                        homeIntent.putExtra("email",emailEditText.text.toString())
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
        val nameEditText:EditText=findViewById(R.id.nombreEditTextView)
        val name:String=nameEditText.text.toString()
        if(emailEditText.text.isNotEmpty() && passwordEditText.text.isNotEmpty()){

            FirebaseAuth.getInstance().signInWithEmailAndPassword(emailEditText.text.toString(),
                passwordEditText.text.toString()).addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    db.collection("users").document(emailEditText.text.toString()).set(
                        hashMapOf("name" to name)
                    )
                    val homeIntent = Intent(this,Profile::class.java)
                    homeIntent.putExtra("email",emailEditText.text.toString())
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