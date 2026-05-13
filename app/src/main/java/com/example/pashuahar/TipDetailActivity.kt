package com.example.pashuahar

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class TipDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(
            R.layout.activity_tip_detail
        )

        val tipTitle =
            intent.getStringExtra(
                "TIP_TITLE"
            ) ?: "Veterinary Tip"

        findViewById<TextView>(
            R.id.tvDetailTitle
        ).text = tipTitle

        val tvContent =
            findViewById<TextView>(
                R.id.tvDetailContent
            )

        tvContent.text =
            when {

                tipTitle.contains(
                    "Vet",
                    ignoreCase = true
                ) ->

                    """
            • Schedule regular veterinary visits.

            • Vaccinate cattle on time.

            • Deworm every 3–6 months.

            • Monitor appetite and milk production.

            • Early disease detection improves cattle health.
            """.trimIndent()

                tipTitle.contains(
                    "Water",
                    ignoreCase = true
                ) ->

                    """
            • Provide fresh clean water daily.

            • Clean water tanks regularly.

            • Avoid stagnant or dirty water.

            • Proper hydration improves digestion.

            • Clean water increases milk yield.
            """.trimIndent()

                tipTitle.contains(
                    "Season",
                    ignoreCase = true
                ) ->

                    """
            • Provide shade during summer.

            • Keep cattle dry during monsoon.

            • Increase fodder during winter.

            • Protect cattle from heat stress.

            • Maintain clean shelter conditions.
            """.trimIndent()

                else ->

                    "Consult your veterinarian for more details."
            }

        findViewById<Button>(
            R.id.btnDetailBack
        ).setOnClickListener {

            finish()
        }
    }
}