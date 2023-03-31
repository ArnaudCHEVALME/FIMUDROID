package com.example.fimudroid.ui.planning

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.fimudroid.R
import com.example.fimudroid.network.models.Concert
import java.time.LocalTime
import java.time.temporal.ChronoUnit
import kotlin.math.absoluteValue

class ConcertView(context: Context) : ConstraintLayout(context) {

    private lateinit var artisteNameTextView: TextView
    private lateinit var artistePaysTextView: TextView

    fun setConcert(concert: Concert) {
        val view = LayoutInflater.from(context).inflate(R.layout.concert_layout, this, true)
        artisteNameTextView = findViewById(R.id.artiste_name_concert_view)
        artistePaysTextView = findViewById(R.id.pays_artiste_concert_view)

        setBackgroundColor(Color.parseColor(concert.artiste?.categorie?.couleur ?: "#BEBEBE"))
        artisteNameTextView.text = concert.artiste?.nom ?: "C'est qui ce pélo ?"
        artistePaysTextView.text =
            concert.artiste?.pays?.joinToString(", ") { it.libelle } ?: "Il vient d'où ? O_o"

        val duree = getTimeDifferenceInMinutes(concert.heure_debut, concert.heure_fin)
        println(duree)

//        val constraintSet = ConstraintSet()
//        constraintSet.clone(this)
//
//        // set the width depending on the length of a concert. echelle x8
//        constraintSet.constrainWidth(id, duree * 8)
//
//        // apply the new constraint set to the view
//        constraintSet.applyTo(this)

        val newLayoutParams = LayoutParams(
            duree * 8,
            LayoutParams.MATCH_PARENT
        )
        view.layoutParams = newLayoutParams
        requestLayout()
    }

    private fun getTimeDifferenceInMinutes(time1: String, time2: String): Int {
        val localTime1 = LocalTime.parse(time1)
        val localTime2 = LocalTime.parse(time2)
        val minutesDifference = ChronoUnit.MINUTES.between(localTime1, localTime2)
        return minutesDifference.toInt().absoluteValue
    }
}