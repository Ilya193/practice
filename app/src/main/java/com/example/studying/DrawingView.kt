package com.example.studying

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import java.io.File
import java.io.FileOutputStream
import java.util.UUID

class DrawingView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var path = Path()

    private var bitmap: Bitmap? = null
    private var canvas: Canvas? = null

    private var paint = Paint().apply {
        color = Color.GREEN
        isAntiAlias = true
        isDither = true
        style = Paint.Style.STROKE
        strokeWidth = 12f
    }

    private var paths = mutableListOf<Path>()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        for (p in paths) {
            canvas.drawPath(p, paint)
        }
        canvas.drawPath(path, paint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                path.reset()
                path.moveTo(x, y)
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                path.lineTo(x, y)
            }
            MotionEvent.ACTION_UP -> {
                paths.add(Path(path))
                path.reset()
            }
        }

        invalidate()
        return true
    }

    fun save() {
        bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        canvas = Canvas(bitmap!!)
        draw(canvas)
        val file = File(context.cacheDir, "${UUID.randomUUID()}.png")
        val stream = FileOutputStream(file)
        bitmap?.compress(Bitmap.CompressFormat.PNG, 100, stream)
        stream.close()
    }

    fun clear() {
        if (paths.isNotEmpty()) {
            path.reset()
            paths.clear()
            invalidate()
        }
    }

    fun undo() {
        if (paths.isNotEmpty()) {
            paths.removeLast()
            invalidate()
        }
    }
}