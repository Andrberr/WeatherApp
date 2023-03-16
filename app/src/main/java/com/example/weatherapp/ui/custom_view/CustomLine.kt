package com.example.weatherapp.ui.custom_view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class CustomLine @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var lineHeight: Float = 0f
    private var curTemp: Float = 0f

    private val rectPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = Color.BLUE
    }

    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = Color.rgb(102,48,199)
        textSize = 60F
    }

    fun setParams(curTemp: Float, minTemp: Float) {
        lineHeight = ((curTemp - minTemp) + 10f) * 10f
        this.curTemp = curTemp
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawRect(
            (width / 3).toFloat(),
            height - lineHeight,
            (2 * width / 3).toFloat(),
            height.toFloat(),
            rectPaint
        )
        canvas.drawText(
            "$curTemp°C",
            (width / 3).toFloat(),
            (height - lineHeight - 10F),
            textPaint
        )
    }
}
