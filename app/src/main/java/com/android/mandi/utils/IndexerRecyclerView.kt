package com.android.mandi.utils

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.os.Handler
import android.os.Message
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import kotlin.math.floor

class IndexerRecyclerView : RecyclerView {
    
    companion object {
        private const val indexerWidth = 25
        private const val indexerHeight = 18
    }
    
    private var density : Float = 160.toFloat()
    private var isIndexerSet = false
    var scaledWidth : Float = 0.toFloat()
    var scaledHeight : Float = 0.toFloat()
    var sections = mutableListOf<String>()
    var sx : Float = 0.toFloat()
    var sy : Float = 0.toFloat()
    var section : String = ""
    var showIndexer = false
    
    constructor(context : Context) : super(context) {
        initialize(context)
    }
    
    constructor(context : Context, attributeSet : AttributeSet) : super(context, attributeSet) {
        initialize(context)
    }
    
    constructor(context : Context, attributeSet : AttributeSet, style : Int) : super(context, attributeSet, style) {
        initialize(context)
    }
    
    private fun initialize(context : Context) {
        density = context.resources.displayMetrics.density
    }
    
    override fun onDraw(canvas : Canvas) {
        if(showIndexer && !isIndexerSet) {
            setupIndexer()
        }
        super.onDraw(canvas)
    }
    
    private fun setupIndexer() {
        val indexes = (adapter as IndexerRecyclerViewListener).getIndex()
        if(null != indexes) {
            val keys = indexes.keys
            val listSection = ArrayList(keys)
            listSection.sort()
            sections.clear()
            for(char in listSection) {
                sections.add(char)
            }
            scaledWidth = indexerWidth * density
            scaledHeight = indexerHeight * density
            sx = this.width.toFloat() - this.paddingRight.toFloat() - (1.2 * scaledWidth).toFloat()
            sy = ((this.height - (scaledHeight * sections.size)) / 2.0).toFloat()
            isIndexerSet = true
        }
    }
    
    @SuppressLint("DefaultLocale", "ClickableViewAccessibility")
    override fun onTouchEvent(event : MotionEvent) : Boolean {
        if(showIndexer && isIndexerSet) {
            val x = event.x
            val y = event.y
            when(event.action) {
                MotionEvent.ACTION_DOWN -> {
                    if(x < sx - scaledWidth || y < sy || y > (sy + scaledHeight * sections.size))
                        return super.onTouchEvent(event)
                    else {
                        val yy = y - this.paddingTop - paddingBottom - sy
                        var currentPosition = floor((yy / scaledHeight).toDouble()).toInt()
                        if(currentPosition < 0) currentPosition = 0
                        if(currentPosition >= sections.size) currentPosition = sections.size - 1
                        section = sections[currentPosition]
                        var positionToScroll = 0
                        val indexes = (adapter as IndexerRecyclerViewListener).getIndex()
                        if(null != indexes) {
                            if(indexes.containsKey(section.toUpperCase())) {
                                val keyPosition = indexes[section.toUpperCase()]
                                if(null != keyPosition) {
                                    positionToScroll = keyPosition
                                }
                            }
                            this.scrollToPosition(positionToScroll)
                            this@IndexerRecyclerView.invalidate()
                        }
                    }
                }
                MotionEvent.ACTION_MOVE -> {
                    if((x < sx - scaledWidth || y < sy || y > (sy + scaledHeight * sections.size)))
                        return super.onTouchEvent(event)
                    else {
                        val yy = y - sy
                        var currentPosition = floor((yy / scaledHeight).toDouble()).toInt()
                        if(currentPosition < 0) currentPosition = 0
                        if(currentPosition >= sections.size) currentPosition = sections.size - 1
                        section = sections[currentPosition]
                        var positionToScroll = 0
                        val indexes = (adapter as IndexerRecyclerViewListener).getIndex()
                        if(null != indexes) {
                            if(indexes.containsKey(section.toUpperCase())) {
                                val keyPosition = indexes[section.toUpperCase()]
                                if(null != keyPosition) {
                                    positionToScroll = keyPosition
                                }
                            }
                            this.scrollToPosition(positionToScroll)
                            this@IndexerRecyclerView.invalidate()
                        }
                    }
                }
                MotionEvent.ACTION_UP -> {
                    return if(x < sx - scaledWidth || y < sy || y > (sy + scaledHeight * sections.size))
                        super.onTouchEvent(event)
                    else
                        true
                }
            }
            return true
        }
        return super.onTouchEvent(event)
    }
}