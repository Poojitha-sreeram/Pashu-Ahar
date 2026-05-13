package com.example.pashuahar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Adapter for displaying feed history reports in RecyclerView.
 */
class FeedHistoryAdapter(

    private var items: List<FeedHistoryItem>,

    private val onItemClick: (FeedHistoryItem) -> Unit,

    private val onDelete: (FeedHistoryItem) -> Unit,

    private val onShare: (FeedHistoryItem) -> Unit

) : RecyclerView.Adapter<FeedHistoryAdapter.ViewHolder>() {

    private val df = DecimalFormat("#.##")

    private val sdf = SimpleDateFormat(
        "dd MMM yyyy, hh:mm a",
        Locale.getDefault()
    )

    /**
     * ViewHolder class
     */
    class ViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {

        val tvBreed: TextView =
            view.findViewById(R.id.tvHistoryBreed)

        val tvDate: TextView =
            view.findViewById(R.id.tvHistoryDate)

        val tvSummary: TextView =
            view.findViewById(R.id.tvHistorySummary)

        val tvTotal: TextView =
            view.findViewById(R.id.tvHistoryTotal)

        val btnDelete: ImageButton =
            view.findViewById(R.id.btnDelete)

        val btnShare: ImageButton =
            view.findViewById(R.id.btnShare)
    }

    /**
     * Inflate history card layout
     */
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.item_feed_history,
                parent,
                false
            )

        return ViewHolder(view)
    }

    /**
     * Bind data to card views
     */
    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {

        val item = items[position]

        // Breed
        holder.tvBreed.text =
            item.breed

        // Date
        holder.tvDate.text =
            sdf.format(Date(item.timestamp))

        // Milk Yield + Savings
        holder.tvSummary.text =
            "Milk Yield: ${df.format(item.milkYield)} L | Savings: ₹${df.format(item.savings)}"

        // Total Feed
        holder.tvTotal.text =
            "Total Feed: ${df.format(item.totalFeed)} kg"

        /**
         * Open details
         */
        holder.itemView.setOnClickListener {
            onItemClick(item)
        }

        /**
         * Delete item
         */
        holder.btnDelete.setOnClickListener {
            onDelete(item)
        }

        /**
         * Share report
         */
        holder.btnShare.setOnClickListener {
            onShare(item)
        }
    }

    /**
     * Total item count
     */
    override fun getItemCount(): Int {
        return items.size
    }

    /**
     * Update RecyclerView data
     */
    fun updateList(newList: List<FeedHistoryItem>) {

        items = newList

        notifyDataSetChanged()
    }
}