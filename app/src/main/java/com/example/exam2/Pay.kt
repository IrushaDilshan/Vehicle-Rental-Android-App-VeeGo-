package com.example.exam2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class Pay : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pay)

        var btnb = findViewById<TextView>(R.id.payback)
        btnb.setOnClickListener {
            var intent = Intent(this, Resivarion::class.java)
            startActivity(intent)
            finish()
        }


    }
}