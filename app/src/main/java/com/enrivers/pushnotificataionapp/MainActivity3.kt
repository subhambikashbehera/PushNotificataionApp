package com.enrivers.pushnotificataionapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.enrivers.pushnotificataionapp.databinding.ActivityMain3Binding
import com.google.firebase.database.FirebaseDatabase
import org.json.JSONObject

class MainActivity3 : AppCompatActivity() {

    private lateinit var binding: ActivityMain3Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main3)


        binding.send.setOnClickListener {
            val userName = binding.userName.text.toString().trim()
            val imageLink = binding.imageLink.text.toString().trim()
            val demoText = binding.demoText.text.toString().trim()
            val features1 = binding.features1.isChecked.toString().trim()
            val features2 = binding.features2.isChecked.toString().trim()
            val features3 = binding.features3.isChecked.toString().trim()
            val features4 = binding.features4.isChecked.toString().trim()


            if (userName.isEmpty()) {
                Toast.makeText(this, "Please enter the username", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (imageLink.isEmpty()) {
                Toast.makeText(this, "Please enter the imageLink", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (demoText.isEmpty()) {
                Toast.makeText(this, "Please enter the demo text", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            sendNotification(
                userName,
                imageLink,
                demoText,
                features1,
                features2,
                features3,
                features4
            )
        }


    }

    private fun sendNotification(
        userName: String,
        imageLink: String,
        demoText: String,
        features1: String,
        features2: String,
        features3: String,
        features4: String
    ) {
        val fireBaseDataBase =
            FirebaseDatabase.getInstance().getReference("MyToken").child(userName)
        fireBaseDataBase.get().addOnCompleteListener {
            if (it.isSuccessful) {
                val token = it.result.value
                if (token!=null){

                    sendNotificationApiCall(
                        token.toString(),
                        imageLink,
                        demoText,
                        features1,
                        features2,
                        features3,
                        features4
                    )
                }else{
                    Toast.makeText(this, "This user is not registered yet", Toast.LENGTH_SHORT).show()
                }



            } else {
                Toast.makeText(this, "This user is not registered yet", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun sendNotificationApiCall(
        token: String,
        imageLink: String,
        demoText: String,
        features1: String,
        features2: String,
        features3: String,
        features4: String
    ) {

        val tokenObject = JSONObject()
        tokenObject.put("to", token)
        val dataObject = JSONObject()
        dataObject.put("id", (1..200000).random())
        dataObject.put("image", imageLink)
        dataObject.put("text", demoText)
        dataObject.put("featuresAvailable_1", features1)
        dataObject.put("featuresAvailable_2", features2)
        dataObject.put("featuresAvailable_3", features3)
        dataObject.put("featuresAvailable_4", features4)
        dataObject.put("notificationVisibility",binding.isShownNotification.isChecked.toString())


        tokenObject.put("data", dataObject)
        val networkRequest = Volley.newRequestQueue(this)
        val jsonRequest =
            object : JsonObjectRequest(
                Method.POST,
                "https://fcm.googleapis.com/fcm/send",
                tokenObject,
                Response.Listener {
                    try {
                        Toast.makeText(this, "Notification Sent", Toast.LENGTH_SHORT).show()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener {
                    try {
                        if (it.networkResponse.statusCode.toString()
                                .contains("4") || it.networkResponse.statusCode.toString()
                                .contains("3")
                        ) {
                            Toast.makeText(
                                this,
                                "Please check with your credentials and try again.",
                                Toast.LENGTH_SHORT
                            ).show()

                        } else {

                            Toast.makeText(
                                this,
                                "Server is busy now , Please wait and try again after some time.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                        Toast.makeText(
                            this,
                            "Please check your Internet connectivity and try again.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }) {
                override fun getHeaders(): MutableMap<String, String> {
                    val map = hashMapOf<String, String>()
                    map["Authorization"] =
                        "key=AAAAex38d38:APA91bFtDAmfgi3vJf99V3KkIO0yZe2cRE-bOtYhN0fZGcCuZK-gqA5MzP-X_PD8sLQqJCgRtPNWhDzxsf8MeTeL2uMoLO04McsIGNEUx5uz5Fj-nByN9IXn0MGzJncqC8Lnk7lnX_mZ"
                    return map
                }

            }
        jsonRequest.retryPolicy = DefaultRetryPolicy(
            10000,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )
        networkRequest.add(jsonRequest)


    }


}