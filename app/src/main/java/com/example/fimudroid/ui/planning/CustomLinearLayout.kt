package com.example.fimudroid.ui.planning

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.widget.LinearLayout
import java.lang.Float

class CustomLinearLayout(context: Context, attrs: AttributeSet?) : LinearLayout(context, attrs) {

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        var i = 0
        while (i <= width){
            canvas?.drawLine(i*60*8f,0f,i*60*8f +10 , height*1f  , Paint() )
            i++
        }
    }

    override fun onDrawForeground(canvas: Canvas?) {
        super.onDrawForeground(canvas)
//        canvas?.drawRect(0f,0f,2000f,2000f, Paint(Color.RED))
    }
}