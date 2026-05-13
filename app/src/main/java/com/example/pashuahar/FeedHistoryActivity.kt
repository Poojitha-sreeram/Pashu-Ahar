package com.example.pashuahar

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FeedHistoryActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView

    private lateinit var emptyView: TextView

    private lateinit var btnClearAll: Button

    private lateinit var adapter: FeedHistoryAdapter

    private lateinit var historyManager: HistoryManager

    private var historyList =
        mutableListOf<FeedHistoryItem>()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContentView(R.layout.activity_feed_history)

        ViewCompat.setOnApplyWindowInsetsListener(
            findViewById(R.id.feed_history_main)
        ) { v, insets ->

            val systemBars =
                insets.getInsets(WindowInsetsCompat.Type.systemBars())

            v.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom
            )

            insets
        }

        // Initialize views
        recyclerView =
            findViewById(R.id.recyclerViewHistory)

        emptyView =
            findViewById(R.id.tvEmptyState)

        btnClearAll =
            findViewById(R.id.btnClearAll)

        historyManager =
            HistoryManager(this)

        // Load history
        historyList =
            historyManager.getAllReports()
                .toMutableList()

        // RecyclerView setup
        adapter = FeedHistoryAdapter(

            items = historyList,

            onItemClick = { item ->
                Toast.makeText(
                    this,
                    "${item.breed} selected",
                    Toast.LENGTH_SHORT
                ).show()
            },

            onDelete = { item ->
                deleteHistoryItem(item)
            },

            onShare = { item ->
                shareHistoryItem(item)
            }
        )

        recyclerView.layoutManager =
            LinearLayoutManager(this)

        recyclerView.adapter = adapter

        recyclerView.itemAnimator =
            DefaultItemAnimator()

        // Empty state
        updateEmptyState()

        // Clear all history
        btnClearAll.setOnClickListener {

            if (historyList.isEmpty()) {

                Toast.makeText(
                    this,
                    "No history available",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            AlertDialog.Builder(this)
                .setTitle("Clear History")
                .setMessage("Delete all saved reports?")
                .setPositiveButton("Yes") { _, _ ->

                    historyManager.clearAllReports()

                    historyList.clear()

                    adapter.updateList(historyList)

                    updateEmptyState()

                    Toast.makeText(
                        this,
                        "History cleared",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                .setNegativeButton("Cancel", null)
                .show()
        }
    }

    /**
     * Delete single item
     */
    private fun deleteHistoryItem(
        item: FeedHistoryItem
    ) {

        historyManager.deleteReport(item.id)

        historyList.remove(item)

        adapter.updateList(historyList)

        updateEmptyState()

        Toast.makeText(
            this,
            "History deleted",
            Toast.LENGTH_SHORT
        ).show()
    }

    /**
     * Share report
     */
    private fun shareHistoryItem(
        item: FeedHistoryItem
    ) {

        val shareText = """

🐄 Pashu-Aahar Feed Report

Breed: ${item.breed}

Milk Yield: ${item.milkYield} L

Total Feed: ${item.totalFeed} kg

Savings: ₹${item.savings}

Generated using Pashu-Aahar
        """.trimIndent()

        val intent =
            Intent(Intent.ACTION_SEND)

        intent.type = "text/plain"

        intent.putExtra(
            Intent.EXTRA_TEXT,
            shareText
        )

        startActivity(
            Intent.createChooser(
                intent,
                "Share Feed Report"
            )
        )
    }

    /**
     * Empty state handling
     */
    private fun updateEmptyState() {

        if (historyList.isEmpty()) {

            recyclerView.visibility = View.GONE

            emptyView.visibility = View.VISIBLE

        } else {

            recyclerView.visibility = View.VISIBLE

            emptyView.visibility = View.GONE
        }
    }
}