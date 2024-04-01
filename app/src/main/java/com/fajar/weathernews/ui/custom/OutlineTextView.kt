package com.fajar.weathernews.ui.custom

import android.content.Context
import android.graphics.Paint
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.compose.ui.graphics.Canvas

class OutlinedTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    private val outlinePaint = Paint().apply {
        style = Paint.Style.STROKE
        color = android.graphics.Color.BLACK // Outline color
        strokeWidth = 5f // Outline width
        isAntiAlias = true
    }


}