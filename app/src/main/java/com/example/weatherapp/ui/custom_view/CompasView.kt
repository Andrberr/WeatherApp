package com.example.weatherapp.ui.custom_view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.example.weatherapp.R
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sqrt

class CompasView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val circlePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = context.getColor(R.color.purple_500)
        strokeWidth = 5F
    }

    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = context.getColor(R.color.text_light)
        textSize = 50F
    }

    private val linePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        color = context.getColor(R.color.middle_red)
        strokeWidth = 5F
    }

    private val pointPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = context.getColor(R.color.middle_red)
        strokeWidth = 5F
    }

    private var direction = 1
    private var arrowY = 0f
    private var arrowX = 0f
    private var degr = 0f
    data class ArrowPoint(val x: Float, val y: Float)

    fun setParams(degree: Float) {
        degr = degree
        if (degree > 180f && degree < 360f) {
            direction = -direction
            degr = 360f - degree
        }
    }

    private fun getArrowCoordinates(degree: Float): ArrowPoint {
        val h = (height / 2).toFloat()
        val w = (width / 2).toFloat()
        val r = sqrt(2 * w * w * (1 - cos(degree * PI.toFloat() / 180f)))
        val y = (h * h + r * r - w * w) / 2 / h

        val c = y * y - 2 * y * h + h * h
        val b = -2 * w
        val d = b * b - 4 * c
        var x = if (d < 0) return ArrowPoint(0f, 0f)
        else if (d == 0f) {
            -b / 2
        } else {
            val x1 = (-b + sqrt(d)) / 2
            val x2 = (-b - sqrt(d)) / 2
            if (x1 > 0) x1 else x2
        }

        if (direction < 0) x = 2 * w - x
        return ArrowPoint(x = x, y = y)
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val point = getArrowCoordinates(degr)
        arrowX = point.x
        arrowY = point.y

        canvas.drawCircle(
            (width / 2).toFloat(),
            (height / 2).toFloat(),
            (width / 2).toFloat(),
            circlePaint
        )

        canvas.drawText(
            "N",
            (width / 2).toFloat() - 15f,
            (height / 2).toFloat() - (width / 2).toFloat() + 45f,
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
            6f,
            (height / 2).toFloat() + 10f,
            textPaint
        )

        canvas.drawText(
            "E",
            width.toFloat() - 37f,
            (height / 2).toFloat() + 10f,
            textPaint
        )

        canvas.drawLine((width / 2).toFloat(), (height / 2).toFloat(), arrowX, arrowY, linePaint)

        canvas.drawCircle((width/2).toFloat(), (height/2).toFloat(), 10f, pointPaint)
    }
}