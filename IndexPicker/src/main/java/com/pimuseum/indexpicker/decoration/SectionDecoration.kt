package com.pimuseum.indexpicker.decoration

import android.content.Context
import android.graphics.*
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pimuseum.indexpicker.R
import com.pimuseum.indexpicker.data.ItemFlag
import com.pimuseum.indexpicker.adapter.CityAdapter
import kotlin.math.roundToInt


class SectionDecoration(private var sectionHeight : Int = 100,
                        private var context : Context,
                        var textColor : Int = Color.WHITE,
                        var contrastColor : Int = Color.BLUE) : RecyclerView.ItemDecoration() {

    private var textPaint = Paint()
    private var bgPaint = Paint()

    init {
        textPaint.isAntiAlias = true
        textPaint.textSize = 48F
        textPaint.color = textColor

        bgPaint.isAntiAlias = true
        bgPaint.color = contrastColor
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        if (view.tag is ItemFlag && (view.tag as ItemFlag).isSectionStart) {
            if (parent.layoutManager is LinearLayoutManager) {
                if ((parent.layoutManager as LinearLayoutManager).orientation == LinearLayoutManager.VERTICAL) {
                    outRect.set(0,sectionHeight,0,0)
                } else {
                    outRect.set(sectionHeight,0,0,0)
                }
            }
        }
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val view = parent.getChildAt(i)
            val position = parent.getChildAdapterPosition(view)

            if (position != RecyclerView.NO_POSITION && (view.tag as ItemFlag).isSectionStart) {
                drawHeader(c, parent, view, position)
            }
        }
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val view1 = parent.getChildAt(0)
        val view2 = parent.getChildAt(1)
        if (view1 != null && view2 != null) {
            val section1 = view1.tag as ItemFlag
            val section2 = view2.tag as ItemFlag

            val position = parent.getChildAdapterPosition(view1)
            val left = parent.paddingLeft
            val right = parent.width - parent.paddingRight
            var bottom = sectionHeight
            var top = 0

            if (section1.isSectionEnd && section2.isSectionStart && view1.bottom <= sectionHeight) {
                bottom = view1.bottom
                top = bottom - sectionHeight
            }
            val targetRect = Rect(left, top, right, bottom)
            val fontMetrics = textPaint.fontMetricsInt
            val baseline = (targetRect.bottom + targetRect.top - fontMetrics.bottom - fontMetrics.top) / 2

            c.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), bgPaint)

            if (parent.adapter is CityAdapter) {
                val initial = (parent.adapter as CityAdapter).list[position].getSpellInitial()
                if (initial.isNotEmpty()) {
                    c.drawText(initial, context.resources.getDimension(R.dimen.pm_20dp),
                        baseline.toFloat(), textPaint)
                }
            }
        }
    }

    private fun drawHeader(c: Canvas, parent: RecyclerView, view: View, position: Int) {
        val params = view.layoutParams as RecyclerView.LayoutParams
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight
        val bottom = view.top - params.topMargin - view.translationY.roundToInt()
        val top = bottom - sectionHeight

        val targetRect = Rect(left, top, right, bottom)
        val fontMetrics = textPaint.fontMetricsInt
        val baseline = (targetRect.bottom + targetRect.top - fontMetrics.bottom - fontMetrics.top) / 2


//        val linearGradient =  LinearGradient(0F, 0F, right.toFloat() - left.toFloat(), 0F,
//            contrastColor, textColor, Shader.TileMode.CLAMP)
//        bgPaint.shader = linearGradient

        c.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), bgPaint)

        if (parent.adapter is CityAdapter) {
            val initial = (parent.adapter as CityAdapter).list[position].getSpellInitial()
            if (initial.isNotEmpty()) {
                c.drawText(initial, context.resources.getDimension(R.dimen.pm_20dp),
                    baseline.toFloat(), textPaint)
            }
        }

    }
}