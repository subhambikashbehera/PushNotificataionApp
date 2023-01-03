package com.enrivers.pushnotificataionapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.enrivers.pushnotificataionapp.databinding.ActivityMainBinding
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : AppCompatActivity() {


    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        supportActionBar?.hide()


        val sharedPreferanceManager = SharedPreferanceManager(this)
        if (sharedPreferanceManager.fetchValue("login") == "true") {
            startActivity(Intent(this, MainActivity2::class.java))
            finish()
        }




        binding.button2.setOnClickListener {
            val userName = binding.editTextTextPersonName.text.toString()
            if (userName.isNullOrEmpty()) {
                Toast.makeText(this, "please enter the userName", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            generateToken(userName)
        }


    }


    private fun generateToken(userName: String) {
        FirebaseMessaging.getInstance().token.addOnCompleteListener {
            if (it.isSuccessful) {
                val dataBase = Firebase.database
                val myRef = dataBase.getReference("MyToken").child(userName)
                myRef.setValue(it.result).addOnCompleteListener { it1 ->
                    if (!it1.isSuccessful) {
                        Toast.makeText(
                            this,
                            "Error while generating the token for this time ${it1.exception}",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        val sharedPreferanceManager = SharedPreferanceManager(this)
                        sharedPreferanceManager.saveData("login","true")
                        sharedPreferanceManager.saveData("userName",userName)
                        startActivity(Intent(this, MainActivity2::class.java))
                        finish()
                    }
                }
            } else {
                Toast.makeText(this, "Error in generating the token", Toast.LENGTH_SHORT).show()
            }
        }


    }


}