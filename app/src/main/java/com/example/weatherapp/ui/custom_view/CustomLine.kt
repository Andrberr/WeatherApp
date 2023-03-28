package com.example.weatherapp.ui.custom_view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import kotlin.math.abs

class CustomLine @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var lineHeight: Float = 10f
    private var curTemp: Float = 0f
    private var x1 = 0f
    private var x2 = 0f
    private var y1 = 0f
    private var y2 = 0f
    private var textY = 0f

    private val rectPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = Color.BLUE
    }

    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = Color.rgb(102, 48, 199)
        textSize = 60F
    }

    private val linePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        color = Color.rgb(102, 48, 199)
        strokeWidth = 5F
    }

    fun setParams(curTemp: Float) {
        this.curTemp = curTemp
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (curTemp != 0f) {
            lineHeight = abs(curTemp) * height / 70f
            x1 = width / 3f
            x2 = 2f * width / 3f

            if (curTemp > 0) {
                y1 = height / 2f - lineHeight
                y2 = height / 2f
                textY = y1 - 5f
            } else if (curTemp < 0) {
                y1 = height / 2f
                y2 = height / 2f + lineHeight
                textY = y2 + 55f
            }

            if (curTemp >= 35f) textY = height / 2f + 30f
            else if (curTemp <= -35f) textY = height / 2f - 30f

            canvas.drawRect(
                x1,
                y1,
                x2,
                y2,
                rectPaint
            )
            canvas.drawText(
                "$curTemp°C",
                (width / 3).toFloat(),
                textY,
                textPaint
            )
        } else{
            canvas.drawText(
                "$curTemp°C",
                (width/3).toFloat(),
                height / 2f - 30f,
                textPaint
            )
        }

        canvas.drawLine(
            0f,
            height / 2.toFloat(),
            width.toFloat(),
            height / 2.toFloat(),
            linePaint
        )
    }
}
