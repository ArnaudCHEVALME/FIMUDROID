package com.example.fimudroid.ui.planning

import android.app.Activity
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.widget.ScrollView
import com.example.fimudroid.R

class CustomView(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    private lateinit var whidthlayout: ScrollView

    private val paint = Paint().apply {
        color = Color.RED
        style = Paint.Style.FILL
    }

/*    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val activity = context as Activity
        whidthlayout = activity.findViewById(R.id.planning_vertical_linear_layout)
        // Mesure la taille du contenu de la vue
        val contentWidth = 10000 // Remplacez cette valeur par la taille réelle de votre contenu
        val contentHeight = 100 // Remplacez cette valeur par la taille réelle de votre contenu

        // Définit la taille de la vue en fonction du contenu et des spécifications de mesure
        val desiredWidth = resolveSize(contentWidth, widthMeasureSpec)
        val desiredHeight = resolveSize(contentHeight, heightMeasureSpec)

        // Appelle la méthode setMeasuredDimension() pour définir la taille de la vue
        setMeasuredDimension(desiredWidth, desiredHeight)
    }*/

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val activity = context as Activity
        whidthlayout = activity.findViewById(R.id.scrollViewA)
        //canvas?.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paint)

        val textPaint = Paint().apply {
            textSize = 50f
            color = Color.BLACK
        }

        val text = "                                 14h                                 15h                                 16h                                 17h                                 18h                                 19h                                 20h                                 21h                                 22h                                 23h                                 00h                                 01h                                 02h                                 03h                                 04h                                 05h                                 06h                                 07h                                 08h                                 09h                                 10h                                "
        canvas?.drawText(text, 0f, height.toFloat() / 2, textPaint)
        setMeasuredDimension(10000, 100)
    }
}
