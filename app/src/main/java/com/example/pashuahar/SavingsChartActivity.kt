package com.example.pashuahar

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter

class SavingsChartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_savings_chart)

        // Receive values from Intent
        val homeCost = intent.getFloatExtra("EXTRA_HOME_COST", 0f)
        val marketCost = intent.getFloatExtra("EXTRA_MARKET_COST", 0f)
        val savings = intent.getFloatExtra("EXTRA_SAVINGS", 0f)

        val barChart = findViewById<BarChart>(R.id.barChart)
        val btnBack = findViewById<Button>(R.id.btnChartBack)

        // Create chart entries
        val entries = ArrayList<BarEntry>()
        entries.add(BarEntry(0f, homeCost))
        entries.add(BarEntry(1f, marketCost))
        entries.add(BarEntry(2f, savings))

        // Dataset
        val dataSet = BarDataSet(entries, "Feed Cost Analysis")

        dataSet.colors = listOf(
            Color.parseColor("#2E7D32"),
            Color.parseColor("#8D6E63"),
            Color.parseColor("#FBC02D")
        )

        dataSet.valueTextColor = Color.BLACK
        dataSet.valueTextSize = 14f

        // BarData
        val barData = BarData(dataSet)
        barData.barWidth = 0.5f

        // Attach data
        barChart.data = barData

        // X-axis labels
        val labels = arrayListOf(
            "Homemade",
            "Market",
            "Savings"
        )

        val xAxis = barChart.xAxis
        xAxis.valueFormatter = IndexAxisValueFormatter(labels)
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.granularity = 1f
        xAxis.setDrawGridLines(false)

        // Chart settings
        barChart.description.isEnabled = false
        barChart.axisRight.isEnabled = false
        barChart.animateY(1500)
        barChart.setFitBars(true)
        barChart.invalidate()

        // Back button
        btnBack.setOnClickListener {
            finish()
        }
    }
}