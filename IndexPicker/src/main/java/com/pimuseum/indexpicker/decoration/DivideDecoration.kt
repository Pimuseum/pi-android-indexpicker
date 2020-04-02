package com.pimuseum.indexpicker.decoration

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pimuseum.indexpicker.R
import kotlin.math.roundToInt


class DivideDecoration(private var dividerHeight : Int = 1,
                       private var context : Context,
                       divideColor : Int = Color.BLUE)
    : RecyclerView.ItemDecoration() {

    private var paint = Paint()

    init {
        paint.isAntiAlias = true
        paint.color = divideColor
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val layoutManager = parent.layoutManager
        if (layoutManager is LinearLayoutManager) {
            if (parent.adapter?.itemCount != null
                && parent.getChildAdapterPosition(view) !=  parent.adapter?.itemCount!! - 1) {
                if (layoutManager.orientation == LinearLayoutManager.VERTICAL) {
                    outRect.set(0,0,0,dividerHeight)
                } else {
                    outRect.set(0,0,dividerHeight,0)
                }
            }
        }
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
        val manager = parent.layoutManager
        if (manager is LinearLayoutManager) {
            if (manager.orientation == LinearLayoutManager.VERTICAL) {
                drawVertical(c, parent)
            } else {
                drawHorizontal(c, parent)
            }
        }
    }

    private fun drawVertical(c: Canvas, parent: RecyclerView) {
        val left = parent.paddingLeft + context.resources.getDimension(R.dimen.pm_20dp)
        val right = parent.width - parent.paddingRight - context.resources.getDimension(R.dimen.pm_15dp)
        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams
            val top = child.bottom + params.bottomMargin + child.translationY.roundToInt()
            val bottom = top + dividerHeight
            c.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), paint)
        }
    }

    private fun drawHorizontal(c: Canvas, parent: RecyclerView) {
        val top = parent.paddingTop + context.resources.getDimension(R.dimen.pm_20dp)
        val bottom = parent.height - parent.paddingBottom - context.resources.getDimension(R.dimen.pm_15dp)
        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams
            val left = child.right + params.rightMargin + child.translationY.roundToInt()
            val right = left + dividerHeight
            c.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), paint)
        }
    }
}
