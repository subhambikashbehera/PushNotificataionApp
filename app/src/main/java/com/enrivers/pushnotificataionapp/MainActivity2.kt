package com.enrivers.pushnotificataionapp

import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.enrivers.pushnotificataionapp.databinding.ActivityMain2Binding

class MainActivity2 : AppCompatActivity() {


    lateinit var binding: ActivityMain2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main2)
        supportActionBar?.hide()


        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.cancel(1000)


        val sharedPreferanceManager = SharedPreferanceManager(this)
        val image = sharedPreferanceManager.fetchValue("image")
        val text = sharedPreferanceManager.fetchValue("text")
        val featuresAvailable_1 = sharedPreferanceManager.fetchValue("featuresAvailable_1")
        val featuresAvailable_2 = sharedPreferanceManager.fetchValue("featuresAvailable_2")
        val featuresAvailable_3 = sharedPreferanceManager.fetchValue("featuresAvailable_3")
        val featuresAvailable_4 = sharedPreferanceManager.fetchValue("featuresAvailable_4")

        binding.features1.isVisible = featuresAvailable_1 == "true"
        binding.features2.isVisible = featuresAvailable_2 == "true"
        binding.features3.isVisible = featuresAvailable_3 == "true"
        binding.features4.isVisible = featuresAvailable_4 == "true"


        Glide.with(this).load(image).into(binding.imageView)
        binding.title.text = text.toString()


        binding.logoutButton.setOnClickListener {
            sharedPreferanceManager.clearValue()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }


    }


}