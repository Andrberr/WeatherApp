package com.example.weatherapp.ui.custom_view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class CompasView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val circlePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = Color.rgb(102, 48, 199)
        strokeWidth = 5F
    }

    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = Color.GREEN
        textSize = 60F
    }

    private val linePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        color = Color.rgb(102, 48, 199)
        strokeWidth = 5F
    }

    fun setParams(degree: Float) {
        var degr = degree
        if (degree > 180f) degr = -360f + degree
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawCircle(
            (width / 2).toFloat(),
            (height / 2).toFloat(),
            (width / 2).toFloat(),
            circlePaint
        )
        canvas.drawText(
            "N",
            (width / 2).toFloat() - 15f,
            (height / 2).toFloat() - (width / 2).toFloat() + 55f,
            textPaint
        )

        canvas.drawText(
            "S",
            (width / 2).toFloat() - 15f,
            (height / 2).toFloat() + (width / 2).toFloat() - 10f,
            textPaint
        )

        canvas.drawText(
            "W",
            10f,
            (height / 2).toFloat() + 10f,
            textPaint
        )

        canvas.drawText(
            "E",
            width.toFloat() - 55f,
            (height / 2).toFloat() + 10f,
            textPaint
        )

        //canvas.drawLine((width/2).toFloat(), (height/2).toFloat(), , , linePaint)
    }
}