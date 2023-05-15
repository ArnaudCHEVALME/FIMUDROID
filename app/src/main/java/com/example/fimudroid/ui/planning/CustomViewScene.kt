package com.example.fimudroid.ui.planning

import android.app.Activity
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
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

  /*  override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        val activity = context as Activity
        ScrollViewPlanning = activity.findViewById(R.id.planning_vertical_linear_layout)

        // Utilisez ViewTreeObserver pour obtenir la hauteur de ScrollViewPlanning
        val vto = ScrollViewPlanning.viewTreeObserver
        vto.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                // Mesure la taille du contenu de la vue
                val contentWidth = 170 // Remplacez cette valeur par la taille réelle de votre contenu

                // Définit une taille de vue minimale en fonction des spécifications de mesure
                val desiredWidth = resolveSize(contentWidth, widthMeasureSpec)

                contentHeight = ScrollViewPlanning.height
                Log.d("DFDFHIUFHUIHFUI", contentHeight.toString())

                // Définit la taille de la vue en fonction du contenu et des spécifications de mesure
                val desiredHeight = resolveSize(contentHeight, heightMeasureSpec)

                // Définit la taille de la vue une fois que la hauteur est disponible
                setMeasuredDimension(desiredWidth, desiredHeight)

                // Une fois que la hauteur est disponible, retirez l'écouteur
                ScrollViewPlanning.viewTreeObserver.removeOnGlobalLayoutListener(this)
            }
        })

        // Définit une taille de vue minimale en fonction des spécifications de mesure
        val contentWidth = 170 // Remplacez cette valeur par la taille réelle de votre contenu
        val desiredWidth = resolveSize(contentWidth, widthMeasureSpec)
        val desiredHeight = resolveSize(0, heightMeasureSpec) // Définir une hauteur initiale arbitraire

        // Définit la taille de la vue avant d'ajouter l'écouteur
        setMeasuredDimension(desiredWidth, desiredHeight)
    }*/

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

        // Dessine des rectangles alternativement en rouge et en bleu
        for (i in 0 until height step 100) {
            val paint = if (i / 500 % 2 == 0) redPaint else bluePaint
            canvas?.drawRect(0f, i.toFloat(), width.toFloat(), (i + 100).toFloat(), paint)
        }

        val textPaint = Paint().apply {
            textSize = 50f
            color = Color.BLACK
        }

        val text = "          15h                                 16h                                 17h                                 18h                                 19h                                 20h                                 21h                                 22h                                 23h                                 00h                                 01h                                 02h                                 03h                                 04h                                 05h                                 06h                                 07h                                 08h                                 09h                                 10h                                "
        canvas?.drawText(text, height.toFloat() / 2, 0f, textPaint)
    }
}