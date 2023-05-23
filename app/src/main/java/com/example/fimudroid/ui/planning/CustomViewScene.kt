package com.example.fimudroid.ui.planning

import android.app.Activity
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import android.widget.ScrollView
import com.example.fimudroid.R

class CustomViewScene(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    private lateinit var ScrollViewPlanning: CustomLinearLayout
    private var contentHeight = 3100 // Initialiser à 0


    private val paint = Paint().apply {
        color = Color.RED
        style = Paint.Style.FILL
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        // Mesure la taille du contenu de la vue
        val contentWidth = 170 // Remplacez cette valeur par la taille réelle de votre contenu
        val contentHeight = 8121 // Remplacez cette valeur par la taille réelle de votre contenu

        // Définit la taille de la vue en fonction du contenu et des spécifications de mesure
        val desiredWidth = resolveSize(contentWidth, widthMeasureSpec)
        val desiredHeight = resolveSize(contentHeight, heightMeasureSpec)

        // Appelle la méthode setMeasuredDimension() pour définir la taille de la vue
        setMeasuredDimension(desiredWidth, desiredHeight)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val redPaint = Paint().apply {
            color = Color.parseColor("#D3D3D3") //#D3D3D3
            style = Paint.Style.FILL
        }

        val bluePaint = Paint().apply {
            color = Color.parseColor("#A8A8A8") //#A8A8A8
            style = Paint.Style.FILL
        }

        val textPaint = Paint().apply {
            color = Color.WHITE
            textSize = 40f
            textAlign = Paint.Align.CENTER
        }

        val tabword = arrayOf("Scene 1", "AUDITORIUM", "Grande Salle", "C.C.I", "CAMPUS", "CONCERTS DE RUE", "CORBIS", "GRANIT", "HOTEL DU DEPART", "JAZZ", "KIOSQUE", "L'ARSENAL", "SALLE DES FÊTES", "SAVOUREUSE", "SCENE DES ENFANTS", "SHOWCASE FB","St CHRISTOPHE")

        for (i in 0 until height step 100) {
            val paint = if (i / 500 % 2 == 0) redPaint else bluePaint
            canvas?.drawRect(0f, i.toFloat(), width.toFloat(), (i + 100).toFloat(), paint)

            if (i % 500 == 0) {
                val textX = width / 2f
                val textY = i.toFloat() + 50f + textPaint.fontMetrics.descent - (paint.strokeWidth / 2)

                // Fait pivoter le canvas de 90 degrés autour du centre du texte
                canvas?.rotate(-90f, textX, textY)


                val index = i / 500 // Index correspondant à l'élément du tableau tabword
                textPaint.typeface = Typeface.defaultFromStyle(Typeface.BOLD)
                canvas?.drawText(tabword[index], textX + 300, textY, textPaint)

                // Rétablit la rotation du canvas à sa position initiale
                canvas?.rotate(90f, textX, textY)
            }
        }

    }






}