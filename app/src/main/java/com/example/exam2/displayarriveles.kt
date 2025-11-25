package com.example.exam2

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ImageView
import android.widget.TextView
import android.widget.ScrollView
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class displayarriveles : AppCompatActivity() {

    private val addCarLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val data = result.data
            val name = data?.getStringExtra("car_name") ?: return@registerForActivityResult
            val price = data.getStringExtra("car_price") ?: "0"
            addCarCard(name, price)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_displayarriveles)

        // Rent buttons → go to Resivarion
        findViewById<Button>(R.id.resevebtn1)?.setOnClickListener {
            startActivity(Intent(this, Resivarion::class.java))
        }
        findViewById<Button>(R.id.resevebtn3)?.setOnClickListener {
            startActivity(Intent(this, Resivarion::class.java))
        }
        findViewById<Button>(R.id.resevebtn4)?.setOnClickListener {
            startActivity(Intent(this, Resivarion::class.java))
        }
        findViewById<Button>(R.id.resevebtn5)?.setOnClickListener {
            startActivity(Intent(this, Resivarion::class.java))
        }

        // add  button → open AddCarActivity for result
        findViewById<Button>(R.id.deleteAll).setOnClickListener {
            val intent = Intent(this, AddCarActivity::class.java)
            addCarLauncher.launch(intent)
        }

        // Notification button → open NotificationActivity
        findViewById<ImageView>(R.id.notificationBtn)?.setOnClickListener {
            startActivity(Intent(this, NotificationActivity::class.java))
        }

        // Username click → open ProfileActivity
        findViewById<TextView>(R.id.usernameTxt)?.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        // Search bar click → open SearchActivity
        findViewById<LinearLayout>(R.id.searchBar)?.setOnClickListener {
            startActivity(Intent(this, SearchActivity::class.java))
        }

        // Category filtering
        findViewById<Button>(R.id.All)?.setOnClickListener { filterByCategory("All") }
        findViewById<Button>(R.id.Axio)?.setOnClickListener { filterByCategory("Axio") }
        findViewById<Button>(R.id.Aqua)?.setOnClickListener { filterByCategory("Aqua") }
        findViewById<Button>(R.id.Prius)?.setOnClickListener { filterByCategory("Prius") }
        findViewById<Button>(R.id.Alto)?.setOnClickListener { filterByCategory("Alto") }
        findViewById<Button>(R.id.Swift)?.setOnClickListener { filterByCategory("Swift") }
    }

    private fun addCarCard(name: String, price: String) {
        val container = findViewById<LinearLayout>(R.id.dynamicCardsContainer)
        val card = LayoutInflater.from(this).inflate(R.layout.item_car_card, container, false)

        val nameTv = card.findViewById<TextView>(R.id.typeValue)
        val priceTv = card.findViewById<TextView>(R.id.priceValue)
        val rentBtn = card.findViewById<Button>(R.id.resevebtn)

    nameTv.text = name
        priceTv.text = "Rs.$price"

        rentBtn.setOnClickListener {
            startActivity(Intent(this, Resivarion::class.java))
        }

    // Tag this card by a derived category (e.g., last word of the name like "Axio") for filtering
    card.tag = extractCategoryFromName(name)
        container.addView(card)
    }

    private fun filterByCategory(category: String) {
        // Parent is the ScrollView's LinearLayout; we'll check both static CardViews and dynamic ones
        val rootList = (findViewById<View>(R.id.scrollView).parent as? View)?.findViewById<LinearLayout>(android.R.id.content)
        val scrollChild = findViewById<ScrollView>(R.id.scrollView).getChildAt(0) as LinearLayout

        // Iterate children of the main vertical list
        for (i in 0 until scrollChild.childCount) {
            val child = scrollChild.getChildAt(i)
            if (child is androidx.cardview.widget.CardView) {
                applyVisibilityForCategory(child, category)
            } else if (child.id == R.id.dynamicCardsContainer && child is LinearLayout) {
                for (j in 0 until child.childCount) {
                    val dynCard = child.getChildAt(j)
                    if (dynCard is androidx.cardview.widget.CardView || dynCard is android.view.View) {
                        applyVisibilityForCategory(dynCard, category)
                    }
                }
            }
        }
    }

    private fun applyVisibilityForCategory(view: View, category: String) {
        if (category == "All") {
            view.visibility = View.VISIBLE
            return
        }
        val tagText = (view.tag as? String) ?: inferTypeFromView(view)
        view.visibility = if (tagText.equals(category, ignoreCase = true)) View.VISIBLE else View.GONE
    }

    private fun inferTypeFromView(view: View): String {
        // Try to find a TextView with known ids to infer type
        val typeName = when (view) {
            is androidx.cardview.widget.CardView -> {
                view.findViewById<TextView?>(R.id.typeValue1)?.text?.toString()
                    ?: view.findViewById<TextView?>(R.id.typeValue3)?.text?.toString()
                    ?: view.findViewById<TextView?>(R.id.typeValue4)?.text?.toString()
                    ?: view.findViewById<TextView?>(R.id.typeValue5)?.text?.toString()
                    ?: view.findViewById<TextView?>(R.id.typeValue6)?.text?.toString()
                    ?: view.findViewById<TextView?>(R.id.typeValue)?.text?.toString()
            }
            else -> view.findViewById<TextView?>(R.id.typeValue)?.text?.toString()
        }
        return typeName ?: ""
    }

    private fun extractCategoryFromName(name: String): String {
        val parts = name.trim().split(" ").filter { it.isNotBlank() }
        if (parts.isEmpty()) return name
        // Use last token as type (e.g., "Toyota Axio" -> "Axio")
        return parts.last()
    }
}
