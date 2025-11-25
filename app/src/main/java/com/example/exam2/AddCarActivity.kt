package com.example.exam2

import android.os.Bundle
import android.app.Activity
import android.content.Intent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.exam2.databinding.ActivityAddCarBinding

class AddCarActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddCarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddCarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.saveButton.setOnClickListener {
            val name = binding.carNameEditText.text.toString().trim()
            val price = binding.carPriceEditText.text.toString().trim()

            if (name.isEmpty() || price.isEmpty()) {
                Toast.makeText(this, "Please enter name and price", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Return the data to caller
            val data = Intent().apply {
                putExtra("car_name", name)
                putExtra("car_price", price)
            }
            setResult(Activity.RESULT_OK, data)
            finish()
        }
    }
}
