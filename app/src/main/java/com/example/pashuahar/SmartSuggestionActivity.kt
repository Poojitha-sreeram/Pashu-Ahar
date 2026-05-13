package com.example.pashuahar

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.card.MaterialCardView
import java.text.DecimalFormat

/**
 * SmartSuggestionActivity displays optimized feed alternatives and estimated savings.
 */
class SmartSuggestionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_smart_suggestion)

        val mainView = findViewById<android.view.View>(R.id.smart_suggestion_main)
        if (mainView != null) {
            ViewCompat.setOnApplyWindowInsetsListener(mainView) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }
        }

        // Receive FeedRecipe object safely
        @Suppress("DEPRECATION")
        val recipe = intent.getSerializableExtra("EXTRA_FEED_RECIPE") as? FeedRecipe

        if (recipe == null) {
            finish()
            return
        }

        // UI Components
        val tvSmartSummary = findViewById<TextView>(R.id.tvSmartSummary)
        val suggestionsContainer = findViewById<LinearLayout>(R.id.suggestionsContainer)
        val tvTotalPotentialSavings = findViewById<TextView>(R.id.tvTotalPotentialSavings)
        val btnApplyPlan = findViewById<Button>(R.id.btnApplyPlan)

        // Set Summary - Removed age, weight, and milkYield as they are not in FeedRecipe model
        tvSmartSummary.text = "Optimized Feed Plan for ${recipe.breed}"

        // Get Optimization Suggestions
        val suggestions = SmartFeedOptimizer.getOptimizedSuggestions(recipe)
        val df = DecimalFormat("#.##")

        // Dynamically add suggestion cards
        val inflater = LayoutInflater.from(this)
        for (suggestion in suggestions) {
            val card = inflater.inflate(R.layout.item_optimization_suggestion, suggestionsContainer, false) as MaterialCardView

            val tvReplacement = card.findViewById<TextView>(R.id.tvReplacement)
            val tvSavingsDetail = card.findViewById<TextView>(R.id.tvSavingsDetail)
            val tvBenefit = card.findViewById<TextView>(R.id.tvBenefit)

            tvReplacement.text = "${suggestion.originalIngredient} ➔ ${suggestion.suggestedAlternative}"
            tvSavingsDetail.text = "Est. Daily Savings: ₹${df.format(suggestion.estimatedSavings)}"
            tvBenefit.text = suggestion.nutritionBenefit

            suggestionsContainer.addView(card)
        }

        // Total Potential Savings
        val totalSavings = SmartFeedOptimizer.calculateTotalPotentialSavings(suggestions)
        tvTotalPotentialSavings.text = "Monthly Potential Savings: ₹${df.format(totalSavings * 30)}"

        btnApplyPlan.setOnClickListener {
            Toast.makeText(this, "Optimized plan applied successfully!", Toast.LENGTH_LONG).show()
            finish()
        }
    }
}