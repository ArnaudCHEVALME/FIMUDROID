package com.example.fimudroid.ui.planning

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.widget.LinearLayout
import java.text.SimpleDateFormat
import java.time.LocalTime
import java.time.temporal.ChronoUnit
import java.util.*
import kotlin.math.absoluteValue

class CustomLinearLayout(context: Context, attrs: AttributeSet?) : LinearLayout(context, attrs) {

    private var earliestTime: String = ""
    private var blackAndTxt = Paint()
    private var gray = Paint()
    private var red = Paint()

    init {
        blackAndTxt.textSize = 50f
        blackAndTxt.color = Color.BLACK
        blackAndTxt.strokeWidth = 5f


        gray.textSize = 50f
        gray.color = Color.parseColor("#BEBEBE")
        gray.strokeWidth = 5f

        red.textSize = 50f
        red.color = Color.RED
        red.strokeWidth = 10f
    }



    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (earliestTime == "")
            return


        val mins = earliestTime.subSequence(3, 5)
        var firstHour = earliestTime.subSequence(0, 2).toString().toInt()

        if (mins != "00"){
            firstHour+= 1
        }
        var i = 0
        val d = getTimeDifferenceInMinutes(earliestTime, "$firstHour:00")

        while (i * 60 * 8f + 300 <= width) {
            canvas?.drawLine(i * 60 * 8f + d  , 100f, i * 60 * 8f + d  , height * 1f, gray)
            //canvas?.drawText("${firstHour+i}h", i * 60 * 8f +d - 35  , 75f, blackAndTxt)
            i++
        }
    }

    override fun onDrawForeground(canvas: Canvas?) {
        super.onDrawForeground(canvas)
        if (earliestTime == "")
            return

        val currentTime = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
        val d = getTimeDifferenceInMinutes(earliestTime, currentTime)

        canvas?.drawLine(d*8f + 300,100f,d*8f + 300,height*1f, red)
    }

    fun setStartTime(time: String) {
        earliestTime = time
    }

    private fun getTimeDifferenceInMinutes(time1: String, time2: String): Int {
        val localTime1 = LocalTime.parse(time1)
        val localTime2 = LocalTime.parse(time2)
        val minutesDifference = ChronoUnit.MINUTES.between(localTime1, localTime2)
        return minutesDifference.toInt().absoluteValue
    }
}