package com.android.mandi.adapters


import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.android.mandi.R
import com.android.mandi.dto.SabjiMandiDto

class SabjiAdapter : BaseRecyclerViewAdapter() {

    companion object {
        private const val LIST_ITEM = 1000
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            LIST_ITEM -> MeetViewHolder(
                inflater.inflate(
                    R.layout.list_item_sabji_list,
                    parent,
                    false
                )
            )
            else -> super.onCreateViewHolder(parent, viewType)
        }
    }

    inner class MeetViewHolder internal constructor(itemView: View) :
        BaseRecyclerViewAdapter.ViewHolder(itemView) {

        private val tvVarietyName: TextView = itemView.findViewById(R.id.tvVarietyName)
        private val tvVarietyCost: TextView = itemView.findViewById(R.id.tvVarietyCost)
        private val tvVarietyApproxCost: TextView = itemView.findViewById(R.id.tvVarietyApproxCost)
        private val tvDateOfArrival: TextView = itemView.findViewById(R.id.tvDateOfArrival)
        private val tvMarketName: TextView = itemView.findViewById(R.id.tvMarketName)
        private val tvLocation: TextView = itemView.findViewById(R.id.tvLocation)


        override fun onBindViewHolder(
            viewItem: RecyclerViewItem,
            onItemClickListener: View.OnClickListener,
            onLongClickListener: View.OnLongClickListener
        ) {
            if (viewItem is SabjiRecord) {

                if (!viewItem.record.variety.isNullOrEmpty()) tvVarietyName.text =
                    viewItem.record.variety
                else tvVarietyName.text = "--"

                if (!viewItem.record.modalPrice.isNullOrEmpty()) tvVarietyCost.text =
                    viewItem.record.modalPrice
                else tvVarietyCost.text = "--"

                if (!viewItem.record.modalPrice.isNullOrEmpty()) tvVarietyCost.text =
                    viewItem.record.modalPrice
                else tvVarietyCost.text = "--"

                val minPrice: String =
                    if (!viewItem.record.minPrice.isNullOrEmpty()) viewItem.record.minPrice!! else "--"
                val maxPrice: String =
                    if (!viewItem.record.maxPrice.isNullOrEmpty()) viewItem.record.maxPrice!! else "--"

                if (minPrice == "--" && maxPrice == "--") {
                    tvVarietyApproxCost.text = ""
                } else {
                    tvVarietyApproxCost.text = "$minPrice/$maxPrice"
                }

                if (!viewItem.record.arrivalDate.isNullOrEmpty()) tvDateOfArrival.text =
                    viewItem.record.arrivalDate
                else tvDateOfArrival.text = "--"

                if (!viewItem.record.market.isNullOrEmpty()) tvMarketName.text =
                    viewItem.record.market + " " + "Market"
                else tvMarketName.text = "--"

//                val district = viewItem.record.district
//                val state = viewItem.record.state
//
//                if (!district.isNullOrEmpty() && !state.isNullOrEmpty()) {
//                    tvLocation.text = "$district, $state"
//                }

                if (!viewItem.record.location.isNullOrEmpty()) {
                    tvLocation.text = viewItem.record.location
                }

            }
        }
    }

    class SabjiRecord(id: Long, record: SabjiMandiDto.Record) : RecyclerViewItem(id) {

        var record: SabjiMandiDto.Record = record
            private set

        override fun getViewType(): Int {
            return LIST_ITEM
        }
    }
}