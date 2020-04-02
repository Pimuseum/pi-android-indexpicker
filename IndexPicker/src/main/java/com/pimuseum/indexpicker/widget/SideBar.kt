package com.pimuseum.indexpicker.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat
import com.pimuseum.indexpicker.R

class SideBar @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var initialTextList = arrayListOf("A","B","C","D","E","F","G","H","I","J","K","L","N","M","O","P","Q","R","S","T","U","V","W","X","Y","Z")
    private var initialTextPaint = Paint()
    private var initialSelectedTextPaint = Paint()
    private var initialTextMaxLength = initialTextPaint.measureText(initialTextList[0])
    private var initialTextHeight = 0F
    private var initialTextSpanHeight = context.resources.getDimensionPixelSize(R.dimen.pm_SideBarDefaultInitialSpan)
    private var currentTouchInitial : String = initialTextList[0]
    private var onSelectInitial : ((initial : String)->Unit)? = null

    init {
        initialTextPaint.isAntiAlias = true
        initialTextPaint.color = ContextCompat.getColor(context,R.color.pm_SideBarDefaultTextColor)
        initialTextPaint.textSize = context.resources.getDimension(R.dimen.pm_SideBarDefaultInitialSize)

        initialSelectedTextPaint.isAntiAlias = true
        initialSelectedTextPaint.color = ContextCompat.getColor(context,R.color.pm_SideBarDefaultSelectedTextColor)
        initialSelectedTextPaint.textSize = context.resources.getDimension(R.dimen.pm_SideBarDefaultInitialSize)

        initialTextHeight = initialTextPaint.fontMetrics.bottom - initialTextPaint.fontMetrics.top
    }

    /**
     * set touch initial listener
     */
    fun setOnSelectInitial(onSelectInitial : ((initial : String)->Unit)) {
        this.onSelectInitial = onSelectInitial
    }

    /**
     * fill initial text data
     */
    fun setInitialTextArray(initialTextList : ArrayList<String>) {
        if (initialTextList.isNotEmpty()) {
            this.initialTextList = initialTextList
            initialTextList.forEach { initial ->
                if (initialTextPaint.measureText(initial) > initialTextMaxLength)
                    initialTextMaxLength = initialTextPaint.measureText(initial)
            }
            currentTouchInitial = this.initialTextList[0]
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        val resultWidthSize: Int
        val resultHeightSize: Int

        //Measure Width
        resultWidthSize = when (widthMode) {
            MeasureSpec.EXACTLY ->
                widthSize
            MeasureSpec.AT_MOST ->
                (initialTextMaxLength + paddingStart + paddingEnd).toInt()
            else ->
                context.resources.getDimensionPixelSize(R.dimen.pm_SideBarDefaultWidth)
        }

        //Measure Height
        resultHeightSize = when (heightMode) {
            MeasureSpec.EXACTLY ->
                heightSize
            MeasureSpec.AT_MOST ->
                (initialTextList.size * (initialTextHeight + initialTextSpanHeight) + paddingTop + paddingBottom).toInt()
            else ->
                context.resources.getDimensionPixelSize(R.dimen.pm_SideBarDefaultWidth)
        }

        setMeasuredDimension(resultWidthSize,resultHeightSize)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.translate((width / 2).toFloat(), 0F)
        initialTextList.forEachIndexed { index, initial ->
            if (currentTouchInitial == initial ) {

                canvas.drawCircle(0F,
                    index * (initialTextHeight + initialTextSpanHeight) - initialTextHeight/2 + initialTextPaint.fontMetrics.bottom + paddingTop,
                    initialTextHeight * 5 / 8,
                    initialTextPaint)

                canvas.drawText(initial,
                    - initialTextPaint.measureText(initial)/2,
                    index * (initialTextHeight + initialTextSpanHeight) + paddingTop, initialSelectedTextPaint)

            } else {
                canvas.drawText(initial,
                    - initialTextPaint.measureText(initial)/2,
                    index * (initialTextHeight + initialTextSpanHeight) + paddingTop, initialTextPaint)
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when(event.action) {
            MotionEvent.ACTION_DOWN -> {
                val index: Int = ((event.y - paddingTop) / (initialTextHeight + initialTextSpanHeight)).toInt()
                if (index >= 0 && index <= initialTextList.size - 1) {
                    currentTouchInitial = initialTextList[index]
                    invalidate()
                    onSelectInitial?.invoke(currentTouchInitial)
                    return true
                }
            }

            MotionEvent.ACTION_MOVE -> {
                val index: Int = ((event.y - paddingTop) / (initialTextHeight + initialTextSpanHeight)).toInt()
                if (index >= 0 && index <= initialTextList.size - 1) {
                    if (currentTouchInitial != initialTextList[index]) {
                        currentTouchInitial = initialTextList[index]
                        invalidate()
                        onSelectInitial?.invoke(currentTouchInitial)
                        return true
                    }
                }
            }
        }
        return false
    }

    /**
     * 获取当前 SideBar 选择的首字母
     */
    fun getCurrentInitial() : String {
        return currentTouchInitial
    }

    fun setCurrentInitial(initial : String) {
        currentTouchInitial = initial
    }
}