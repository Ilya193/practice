package com.example.studying

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class CustomView : View {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    private val paintCircle = Paint().apply {
        strokeWidth = 30F
        style = Paint.Style.STROKE
        isAntiAlias = true
        color = Color.BLACK
    }

    private val paintLine = Paint().apply {
        strokeWidth = 30F
        style = Paint.Style.FILL
        isAntiAlias = true
        color = Color.BLACK
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        val centerX = (width / 2).toFloat()
        val centerY = (height / 2).toFloat()

        canvas?.apply {
            drawLine(centerX, 150f, centerX, 500f, paintLine)
            drawLine(centerX - 175,
                centerY, centerX + 175,
                centerY, paintLine)
            drawLine(centerX - 135, 200f, centerX + 140, 457f, paintLine)
            drawLine(centerX + 135, 200f, centerX - 140, 457f, paintLine)
            drawCircle(centerX, centerY, 175f, paintCircle)
        }
    }
}