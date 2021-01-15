package com.android.mandi.adapters


import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.android.mandi.R
import com.android.mandi.dto.SabjiMandiDto

class LocationAdapter : BaseRecyclerViewAdapter() {

    companion object {
        private const val LIST_ITEM = 1000
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            LIST_ITEM -> MeetViewHolder(
                inflater.inflate(
                    R.layout.list_item_location,
                    parent,
                    false
                )
            )
            else -> super.onCreateViewHolder(parent, viewType)
        }
    }

    inner class MeetViewHolder internal constructor(itemView: View) :
        BaseRecyclerViewAdapter.ViewHolder(itemView) {

        private val cbLocation: CheckBox = itemView.findViewById(R.id.cbLocation)


        override fun onBindViewHolder(
            viewItem: RecyclerViewItem,
            onItemClickListener: View.OnClickListener,
            onLongClickListener: View.OnLongClickListener
        ) {
            if (viewItem is LocationRecord) {
                cbLocation.text = viewItem.record.name
                cbLocation.isChecked = viewItem.record.isSelected

                cbLocation.setOnClickListener {
                    viewItem.record.isSelected = cbLocation.isChecked
                }
            }
        }
    }

    class LocationRecord(id: Long, record: SabjiMandiDto.Location) : RecyclerViewItem(id) {

        var record: SabjiMandiDto.Location = record
            private set

        override fun getViewType(): Int {
            return LIST_ITEM
        }
    }
}