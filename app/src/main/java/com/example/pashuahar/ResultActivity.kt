package com.example.pashuahar

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.Locale

class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_result)

        /**
         * Retrieve FeedRecipe safely
         */
        @Suppress("DEPRECATION")
        val recipe =
            intent.getSerializableExtra(
                "EXTRA_FEED_RECIPE"
            ) as? FeedRecipe

        /**
         * Display Results
         */
        if (recipe != null) {
            displayResults(recipe)
        }

        /**
         * View Savings Chart
         */
        findViewById<Button>(R.id.costSavingsButton)
            .setOnClickListener {

                if (recipe != null) {

                    /**
                     * Cost Calculations
                     */
                    val homeCost =
                        recipe.dailyCost

                    val marketCost =
                        (
                                recipe.maize +
                                        recipe.cottonseedCake +
                                        recipe.greenFodder +
                                        recipe.dryFodder
                                ) * 32f

                    val savings =
                        marketCost - homeCost

                    /**
                     * Open SavingsChartActivity
                     */
                    val intent =
                        Intent(
                            this,
                            SavingsChartActivity::class.java
                        )

                    intent.putExtra(
                        "EXTRA_HOME_COST",
                        homeCost
                    )

                    intent.putExtra(
                        "EXTRA_MARKET_COST",
                        marketCost
                    )

                    intent.putExtra(
                        "EXTRA_SAVINGS",
                        savings
                    )

                    startActivity(intent)
                }
            }

        /**
         * Back Button
         */
        findViewById<Button>(R.id.backButton)
            .setOnClickListener {
                finish()
            }
    }

    private fun displayResults(
        recipe: FeedRecipe
    ) {

        /**
         * Breed
         */
        findViewById<TextView>(R.id.breedDisplay)
            .text =
            "Breed: ${recipe.breed}"

        /**
         * Summary
         */
        findViewById<TextView>(R.id.inputSummary)
            .text =
            "Nutritional Analysis for ${recipe.breed}"

        /**
         * Feed Recommendation
         */
        findViewById<TextView>(R.id.totalFeedText)
            .text =
            String.format(
                Locale.getDefault(),
                "Total Feed: %.2f kg/day",
                recipe.totalDMI
            )

        findViewById<TextView>(R.id.maizeText)
            .text =
            String.format(
                Locale.getDefault(),
                "Maize: %.2f kg",
                recipe.maize
            )

        findViewById<TextView>(R.id.cottonseedText)
            .text =
            String.format(
                Locale.getDefault(),
                "Cottonseed Cake: %.2f kg",
                recipe.cottonseedCake
            )

        findViewById<TextView>(R.id.mineralText)
            .text =
            String.format(
                Locale.getDefault(),
                "Mineral Mixture: %.2f kg",
                recipe.mineralMixture
            )

        /**
         * Nutrition Summary
         */
        findViewById<TextView>(R.id.tvProteinLevel)
            .text =
            String.format(
                Locale.getDefault(),
                "Protein Level: %.1f%%",
                recipe.estimatedProtein
            )

        findViewById<TextView>(R.id.tvEnergyReq)
            .text =
            String.format(
                Locale.getDefault(),
                "Energy Requirement: %.2f Mcal/kg",
                recipe.estimatedEnergy
            )

        findViewById<TextView>(R.id.tvDMI)
            .text =
            String.format(
                Locale.getDefault(),
                "Total DMI: %.2f kg/day",
                recipe.totalDMI
            )

        /**
         * Feed Quality
         */
        val quality =
            if (recipe.estimatedProtein >= 15f)
                "High Quality"
            else
                "Standard"

        findViewById<TextView>(R.id.tvFeedQuality)
            .text =
            "Feed Quality: $quality"

        /**
         * Cost Savings
         */
        val homeCost =
            recipe.dailyCost

        val marketCost =
            (
                    recipe.maize +
                            recipe.cottonseedCake +
                            recipe.greenFodder +
                            recipe.dryFodder
                    ) * 32f

        val savings =
            marketCost - homeCost

        findViewById<TextView>(R.id.tvHomeCost)
            .text =
            String.format(
                Locale.getDefault(),
                "Homemade Feed Cost: ₹ %.2f",
                homeCost
            )

        findViewById<TextView>(R.id.tvMarketCost)
            .text =
            String.format(
                Locale.getDefault(),
                "Market Feed Cost: ₹ %.2f",
                marketCost
            )

        findViewById<TextView>(R.id.tvSavings)
            .text =
            String.format(
                Locale.getDefault(),
                "Estimated Savings: ₹ %.2f",
                savings
            )
    }
}