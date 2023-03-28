package com.example.fimudroid.ui.planning

import android.content.Context
import android.view.LayoutInflater
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
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

        artisteNameTextView.text = concert.artiste?.nom ?: "Na"
        artistePaysTextView.text = concert.artiste?.pays?.joinToString(", ") { it.libelle } ?: ""


        val dur = getTimeDifferenceInMinutes(concert.heure_debut, concert.heure_fin) * 8

        println(dur)

        // set the width depending on the length of a concert
        layoutParams = LayoutParams(dur, LayoutParams.MATCH_PARENT)

        // TODO - trouver un moyen de faire le resize de merde
        // Request a layout pass to recalculate constraints for child views
        requestLayout()
    }

    private fun getTimeDifferenceInMinutes(time1: String, time2: String): Int {
        val localTime1 = LocalTime.parse(time1)
        val localTime2 = LocalTime.parse(time2)
        val minutesDifference = ChronoUnit.MINUTES.between(localTime1, localTime2)
        return minutesDifference.toInt().absoluteValue
    }
}