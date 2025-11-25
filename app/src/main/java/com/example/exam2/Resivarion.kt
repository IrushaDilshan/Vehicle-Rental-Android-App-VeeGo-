package com.example.exam2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class Resivarion : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resivarion)




        var btn = findViewById<TextView>(R.id.resivationbtn)
        btn.setOnClickListener {
            var intent = Intent(this, Pay::class.java)
            startActivity(intent)
            finish()
        }
        var btnb = findViewById<TextView>(R.id.reseveback)
        btnb.setOnClickListener {
            var intent = Intent(this, displayarriveles::class.java)
            startActivity(intent)
            finish()
        }
    }
}