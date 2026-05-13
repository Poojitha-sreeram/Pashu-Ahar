package com.example.pashuahar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class TipsAdapter(
    private var tipsList: List<Tip>,
    private val onItemClick: (Tip) -> Unit
) : RecyclerView.Adapter<TipsAdapter.TipViewHolder>() {

    class TipViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        val cardTip: CardView =
            itemView.findViewById(R.id.cardTip)

        val tvTitle: TextView =
            itemView.findViewById(R.id.tvTipTitle)

        val tvDesc: TextView =
            itemView.findViewById(R.id.tvTipDescription)

        val ivIcon: ImageView =
            itemView.findViewById(R.id.ivTipIcon)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TipViewHolder {

        val view =
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.item_tip,
                    parent,
                    false
                )

        return TipViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: TipViewHolder,
        position: Int
    ) {

        val tip =
            tipsList[position]

        /**
         * Set Title
         */
        holder.tvTitle.text =
            tip.title

        /**
         * Set Description
         */
        holder.tvDesc.text =
            tip.description

        /**
         * Set Icon
         */
        holder.ivIcon.setImageResource(
            tip.icon
        )

        /**
         * Card Click
         */
        holder.cardTip.setOnClickListener {

            onItemClick(tip)
        }
    }

    override fun getItemCount(): Int {

        return tipsList.size
    }

    /**
     * Update Filtered List
     */
    fun updateList(
        newList: List<Tip>
    ) {

        tipsList = newList

        notifyDataSetChanged()
    }
}