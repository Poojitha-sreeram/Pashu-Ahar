package com.example.pashuahar

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.Serializable
import java.text.DecimalFormat
import java.util.Locale

/**
 * CostComparisonActivity
 * Displays homemade feed cost vs market cost and navigates to analytics.
 */
class CostComparisonActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_cost_comparison)

        // Setup Edge-to-Edge padding
        val mainView = findViewById<android.view.View>(R.id.cost_comparison_main)
        if (mainView != null) {
            ViewCompat.setOnApplyWindowInsetsListener(mainView) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }
        }

        // Receive FeedRecipe safely from the original FeedRecipe.kt definition
        @Suppress("DEPRECATION")
        val recipe = intent.getSerializableExtra("EXTRA_FEED_RECIPE") as? FeedRecipe

        if (recipe == null) {
            Log.e("PashuAhar", "FeedRecipe is null in CostComparisonActivity")
            finish()
            return
        }

        // UI Components
        val tvHomemadeCost = findViewById<TextView>(R.id.tvHomemadeCost)
        val tvMarketCost = findViewById<TextView>(R.id.tvMarketCost)
        val tvSavings = findViewById<TextView>(R.id.tvSavings)
        val btnSavingsAnalytics = findViewById<Button>(R.id.btnSavingsAnalytics)
        val btnBackHome = findViewById<Button>(R.id.btnBackHome)

        // Cost Calculations
        // Total weight of the current recipe (as-fed)
        val totalWeight = recipe.maize + recipe.cottonseedCake + recipe.dryFodder + recipe.greenFodder

        val homemadeCost = recipe.dailyCost.toDouble()
        // Market cost calculation (Estimated at ₹32/kg for balanced cattle feed)
        val marketCost = totalWeight * 32.0
        val savings = marketCost - homemadeCost

        // Formatter
        val df = DecimalFormat("#.##")

        // Display Values
        tvHomemadeCost.text = "₹ ${df.format(homemadeCost)}"
        tvMarketCost.text = "₹ ${df.format(marketCost)}"
        tvSavings.text = "₹ ${df.format(savings)}"

        // Savings Analytics Button (Navigates to SavingsChartActivity)
        btnSavingsAnalytics.setOnClickListener {
            val intent = Intent(this, SavingsChartActivity::class.java)
            intent.putExtra("EXTRA_HOME_COST", homemadeCost.toFloat())
            intent.putExtra("EXTRA_MARKET_COST", marketCost.toFloat())
            intent.putExtra("EXTRA_SAVINGS", savings.toFloat())
            startActivity(intent)
        }

        // Back Home Button
        btnBackHome.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }
    }
}