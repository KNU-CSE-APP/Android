package com.example.knucseapp.ui.mypage

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.Log
import androidx.annotation.ColorInt
import androidx.recyclerview.widget.RecyclerView

class DividerItemDecoration(
    private val height: Float,
    private val padding: Float,
    @ColorInt
    private val color: Int

) : RecyclerView.ItemDecoration() {

    private val paint = Paint()

    init {
        paint.color = color
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val left = parent.paddingStart + 50.toFloat()
        val right = parent.width - parent.paddingEnd - 50.toFloat()


        for (i in 0 until parent.childCount-1)
        {
            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams
            val top = (child.bottom + params.bottomMargin).toFloat()
            val bottom = top + height
            c.drawRect(left, top, right, bottom, paint)
        }
    }

}