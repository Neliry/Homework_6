package com.example.maria.homework_6

import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import android.view.View

internal class ListItemDecoration(context: Context) : RecyclerView.ItemDecoration() {

    private val divider: Drawable
    private var mPaint: Paint = Paint()

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view)
        val viewType = parent.adapter!!.getItemViewType(position)
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        mPaint.style = Paint.Style.FILL
        for (i in 0 until parent.childCount) {
            val view = parent.getChildAt(i)
            mPaint.color = Color.parseColor("#ababab")
            c.drawRect(view.left.toFloat(), view.bottom.toFloat(), view.right.toFloat(), (view.bottom + 1.px).toFloat(), mPaint)

        }
    }

    init {
        val a = context.obtainStyledAttributes(ATTRS)
        divider = a.getDrawable(0)
        a.recycle()
    }

    val Int.px: Int
        get() = (this * Resources.getSystem().displayMetrics.density).toInt()

    companion object {
        private val ATTRS = intArrayOf(android.R.attr.listDivider)
    }
}

