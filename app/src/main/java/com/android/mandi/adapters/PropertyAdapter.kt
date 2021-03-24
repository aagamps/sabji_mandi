package com.android.mandi.adapters


import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import com.android.mandi.PropertyInterface
import com.android.mandi.R
import com.android.mandi.dto.PropertyMatchDto
import com.android.mandi.viewModel.ScrollingViewModel
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup


class PropertyAdapter constructor(
    private val viewModel: ScrollingViewModel,
    private val propertyInterface: PropertyInterface
) :
    BaseRecyclerViewAdapter() {

    companion object {
        private const val LIST_ITEM = 1000
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            LIST_ITEM -> MeetViewHolder(
                inflater.inflate(
                    R.layout.list_item_facility_list,
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
        private val chipGroup: ChipGroup = itemView.findViewById(R.id.chipGroup)

        override fun onBindViewHolder(
            viewItem: RecyclerViewItem,
            onItemClickListener: View.OnClickListener,
            onLongClickListener: View.OnLongClickListener
        ) {
            val context = itemView.context
            if (viewItem is FacilityRecord) {
                tvVarietyName.text = viewItem.record.name
                for (chipObj in viewItem.record.options) {
                    val icon = chipObj.icon!!.replace("-", "_")
                    val uri = "@drawable/${icon}"
                    val imageResource: Int =
                        context.resources.getIdentifier(uri, null, context.packageName)
                    val res: Drawable = context.resources.getDrawable(imageResource)
                    val chip = Chip(context)
                    chip.textSize = 17f
                    chip.chipStartPadding = 20f
                    chip.chipEndPadding = 20f
                    chip.text = chipObj.name
                    chip.chipIcon = res
                    chip.isCheckable = true
                    chip.checkedIcon = null
                    chip.id = chipObj.optionsIId!!.toInt()
                    chip.chipBackgroundColor =
                        AppCompatResources.getColorStateList(
                            context,
                            R.color.background_chip_state
                        )
                    chip.isEnabled = chipObj.isEnabled
                    chip.isChecked = chipObj.isChecked
                    chipGroup.addView(chip)
                }

                chipGroup.setOnCheckedChangeListener() { _: ChipGroup, optionId: Int ->
                    chipGroup.check(optionId)
                    val exclusionsList = viewModel.getBoundModel()?.exclusionsList!!
                    val optionsList = viewModel.getBoundModel()?.optionsList!!
                    val optionIdsToBeDisabled = ArrayList<String>()
                    val pairId = ArrayList<String>()
                    for (exclusionsObj in exclusionsList) {
                        if (optionId.toString() == exclusionsObj.optionsIId) {
                            pairId.add(exclusionsObj.pairId!!)
                        }
                    }
                    for (exclusionsObj in exclusionsList) {
                        if (exclusionsObj.pairId in pairId && exclusionsObj.optionsIId != optionId.toString()) {
                            optionIdsToBeDisabled.add(exclusionsObj.optionsIId!!)
                        }
                    }
                    for (optionsObj in optionsList) {
                        optionsObj.isEnabled = optionsObj.optionsIId !in optionIdsToBeDisabled
                        optionsObj.isChecked = optionsObj.optionsIId == optionId.toString()
                    }
                    viewModel.getBoundModel()?.optionsList = optionsList
                    propertyInterface.generateList()
                }
            }
        }
    }

    class FacilityRecord(id: Long, record: PropertyMatchDto.FacilitiesObj) : RecyclerViewItem(id) {

        var record: PropertyMatchDto.FacilitiesObj = record
            private set

        override fun getViewType(): Int {
            return LIST_ITEM
        }
    }
}