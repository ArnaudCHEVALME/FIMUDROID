package com.example.fimudroid.ui.planning

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.LayoutInflater
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.example.fimudroid.R
import com.example.fimudroid.network.models.Concert
import java.time.LocalTime
import java.time.temporal.ChronoUnit
import kotlin.math.absoluteValue

class ConcertView(context: Context, concert: Concert) : ConstraintLayout(context) {

    private val artisteNameTextView: TextView
    private val artistePaysTextView: TextView

    init {
        LayoutInflater.from(context).inflate(R.layout.concert_layout, this, true)
        artisteNameTextView = findViewById(R.id.artiste_name_concert_view)
        artistePaysTextView = findViewById(R.id.pays_artiste_concert_view)

        setBackgroundColor(Color.parseColor(concert.artiste?.categorie?.couleur ?: "#888888"))
        artisteNameTextView.text = concert.artiste?.nom ?: "C'est qui ce pélo ?"
        artistePaysTextView.text =
            concert.artiste?.pays?.joinToString(", ") { it.libelle } ?: "Il vient d'où ? O_o"


        val duree = getTimeDifferenceInMinutes(concert.heure_debut, concert.heure_fin)

        // set the width depending on the length of a concert
        layoutParams = LayoutParams(duree * 8, LayoutParams.MATCH_PARENT)

        // Create a vertical packed chain

        val constraintSet = ConstraintSet()
        constraintSet.clone(this)

        constraintSet.connect(
            artisteNameTextView.id, ConstraintSet.TOP,
            id, ConstraintSet.TOP
        )
        constraintSet.connect(
            artisteNameTextView.id, ConstraintSet.START,
            id, ConstraintSet.START
        )
        constraintSet.connect(
            artisteNameTextView.id, ConstraintSet.END,
            id, ConstraintSet.END
        )
        constraintSet.setVerticalChainStyle(
            artisteNameTextView.id, ConstraintSet.CHAIN_PACKED
        )
        constraintSet.setVerticalChainStyle(
                artistePaysTextView.id, ConstraintSet.CHAIN_PACKED
        )

        constraintSet.connect(
            artistePaysTextView.id, ConstraintSet.TOP,
            artisteNameTextView.id, ConstraintSet.BOTTOM
        )
        constraintSet.connect(
            artistePaysTextView.id, ConstraintSet.START,
            id, ConstraintSet.START
        )
        constraintSet.connect(
            artistePaysTextView.id, ConstraintSet.END,
            id, ConstraintSet.END
        )
        constraintSet.connect(
            artistePaysTextView.id, ConstraintSet.BOTTOM,
            id, ConstraintSet.BOTTOM
        )

        constraintSet.applyTo(this)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawCircle(0f,0f,50f, Paint())
    }

    private fun getTimeDifferenceInMinutes(time1: String, time2: String): Int {
        val localTime1 = LocalTime.parse(time1)
        val localTime2 = LocalTime.parse(time2)
        val minutesDifference = ChronoUnit.MINUTES.between(localTime1, localTime2)
        return minutesDifference.toInt().absoluteValue
    }
}