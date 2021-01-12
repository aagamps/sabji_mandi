package com.android.mandi.utils

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

class NoSwipePager : ViewPager {
    
    constructor(context : Context) : super(context)
    
    constructor(context : Context, attributeSet : AttributeSet) : super(context, attributeSet)
    
    private var isSwipeEnabled : Boolean = false
    
    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(motionEvent : MotionEvent?) : Boolean {
        if(isSwipeEnabled) {
            return super.onTouchEvent(motionEvent)
        }
        return false
    }
    
    override fun onInterceptTouchEvent(motionEvent : MotionEvent?) : Boolean {
        if(isSwipeEnabled) {
            return super.onInterceptTouchEvent(motionEvent)
        }
        return false
    }
    
    internal fun setSwipeEnabled() {
        isSwipeEnabled = true
    }
}