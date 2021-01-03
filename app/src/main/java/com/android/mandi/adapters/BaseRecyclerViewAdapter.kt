package com.android.mandi.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.android.mandi.R
import kotlin.properties.Delegates

abstract class BaseRecyclerViewAdapter : RecyclerView.Adapter<BaseRecyclerViewAdapter.ViewHolder>(),
    View.OnClickListener, View.OnLongClickListener {

    companion object {
        private const val LIST_ITEM_LOADING = -10000
        private const val LIST_ITEM_TAP_RETRY = -10001
    }

    protected var recyclerViewItems = mutableListOf<RecyclerViewItem>()
    private var recyclerViewItemClickListener: RecyclerViewItemClickListener? = null
    private var recyclerViewLItemLongClickListener: RecyclerViewItemLongClickListener? = null

    internal interface RecyclerViewItemClickListener {
        fun onRecyclerViewItemClicked(
            @NonNull viewItem: RecyclerViewItem,
            @NonNull view: View,
            @NonNull viewHolder: RecyclerView.ViewHolder,
            position: Int
        )
    }

    internal interface RecyclerViewItemLongClickListener {
        fun onRecyclerViewLongItemClicked(
            @NonNull viewItem: RecyclerViewItem,
            @NonNull view: View,
            @NonNull viewHolder: RecyclerView.ViewHolder,
            position: Int
        )
    }

    internal fun setRecyclerViewItemClickListener(recyclerViewItemClickListener: RecyclerViewItemClickListener?) {
        this.recyclerViewItemClickListener = recyclerViewItemClickListener
    }

    internal fun setRecyclerViewItemLongClickListener(recyclerViewItemLongClickListener: RecyclerViewItemLongClickListener?) {
        this.recyclerViewLItemLongClickListener = recyclerViewItemLongClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            LIST_ITEM_LOADING -> {
                LoadingViewHolder(inflater.inflate(R.layout.loading, parent, false))
            }
            LIST_ITEM_TAP_RETRY -> {
                TapToRetryViewHolder(inflater.inflate(R.layout.tap_to_retry, parent, false))
            }
            else -> {
                throw RuntimeException("Invalid Recycler ViewType")
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val viewItem = recyclerViewItems[position]
        holder.onBindViewHolder(viewItem, this,this)
    }

    override fun getItemCount(): Int {
        return recyclerViewItems.size
    }

    override fun getItemViewType(position: Int): Int {
        return recyclerViewItems[position].getViewType()
    }

    internal fun getViewItems(): List<RecyclerViewItem> {
        return recyclerViewItems
    }

    internal fun addRecyclerViewItem(viewItem: RecyclerViewItem) {
        recyclerViewItems.add(viewItem)
        notifyItemInserted(itemCount - 1)
    }

    internal fun addRecyclerViewItem(position: Int, viewItem: RecyclerViewItem) {
        recyclerViewItems.add(position, viewItem)
        notifyItemInserted(position)
    }

    internal fun addRecyclerViewItems(viewItems: List<RecyclerViewItem>) {
        recyclerViewItems.addAll(viewItems)
        notifyDataSetChanged()
    }

    internal fun removeViewItem(position: Int) {
        recyclerViewItems.removeAt(position)
        notifyItemRemoved(position)
    }

    internal fun removeLoadingViewItem(): Boolean {
        return removeRecyclerViewItemWithId(LIST_ITEM_LOADING.toLong())
    }

    internal fun removeTapToRetryViewItem(): Boolean {
        return removeRecyclerViewItemWithId(LIST_ITEM_TAP_RETRY.toLong())
    }

    private fun removeRecyclerViewItemWithId(itemId: Long): Boolean {
        for ((index, viewItem) in recyclerViewItems.withIndex()) {
            if (viewItem.id == itemId) {
                recyclerViewItems.removeAt(index)
                notifyItemRemoved(index)
                return true
            }
        }
        return false
    }

    internal fun getRecyclerViewItem(position: Int): RecyclerViewItem {
        return recyclerViewItems[position]
    }

    internal fun getRecyclerViewItemWithId(itemId: Long): RecyclerViewItem? {
        for (viewItem in recyclerViewItems) {
            if (viewItem.id == itemId) {
                return viewItem
            }
        }
        return null
    }

    override fun onClick(clickedView: View?) {
        val viewHolder = clickedView?.tag as RecyclerView.ViewHolder
        val position = viewHolder.adapterPosition
        if (position == RecyclerView.NO_POSITION || position >= itemCount) {
            return
        }
        recyclerViewItemClickListener?.onRecyclerViewItemClicked(
            recyclerViewItems[position],
            clickedView,
            viewHolder,
            position
        )
    }

    override fun onLongClick(clickedView: View?): Boolean {
        val viewHolder = clickedView?.tag as RecyclerView.ViewHolder
        val position = viewHolder.adapterPosition
        if (position == RecyclerView.NO_POSITION || position >= itemCount) {
            return true
        }
        recyclerViewLItemLongClickListener?.onRecyclerViewLongItemClicked(
            recyclerViewItems[position],
            clickedView,
            viewHolder,
            position
        )
        return true
    }

    @Suppress("LeakingThis")
    abstract class ViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.tag = this
        }

        abstract fun onBindViewHolder(
            viewItem: RecyclerViewItem,
            onItemClickListener: View.OnClickListener,
            onLongClickListener: View.OnLongClickListener
        )
    }

    abstract class RecyclerViewItem constructor(itemId: Long) {

        var id: Long by Delegates.notNull()

        init {
            this.id = itemId
        }

        internal abstract fun getViewType(): Int
    }

    inner class LoadingViewHolder constructor(itemView: View) : ViewHolder(itemView) {

        override fun onBindViewHolder(
            viewItem: RecyclerViewItem,
            onItemClickListener: View.OnClickListener,
            onLongClickListener: View.OnLongClickListener
        ) = Unit
    }

    inner class TapToRetryViewHolder constructor(itemView: View) : ViewHolder(itemView) {

        private val tapToRetryMessage: TextView?

        init {
            itemView.tag = this
            tapToRetryMessage = itemView.findViewById(R.id.tapToRetryMessage)
        }

        override fun onBindViewHolder(
            viewItem: RecyclerViewItem,
            onItemClickListener: View.OnClickListener,
            onLongClickListener: View.OnLongClickListener
        ) {
            val item = viewItem as TapToRetryViewItem
            itemView.setOnClickListener(onItemClickListener)
            itemView.setOnLongClickListener(onLongClickListener)
            tapToRetryMessage?.text = item.title
        }
    }

    inner class TapToRetryViewItem constructor(title: String, nextPage: Int) :
        RecyclerViewItem(LIST_ITEM_TAP_RETRY.toLong()) {

        var title: String by Delegates.notNull()
        private var nextPage: Int by Delegates.notNull()

        init {
            this.title = title
            this.nextPage = nextPage
        }

        override fun getViewType(): Int {
            return LIST_ITEM_TAP_RETRY
        }
    }

    internal fun clear() {
        recyclerViewItems.clear()
        notifyDataSetChanged()
    }
}