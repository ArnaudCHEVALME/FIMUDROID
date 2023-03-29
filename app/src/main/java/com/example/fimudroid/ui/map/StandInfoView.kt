package com.example.fimudroid.ui.map

import android.content.Context
import android.view.LayoutInflater
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.fimudroid.R
import com.example.fimudroid.network.models.Service
import com.example.fimudroid.network.models.Stand
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import org.osmdroid.util.GeoPoint

class StandInfoView(context: Context, stand: Stand) : ConstraintLayout(context) {

    init {
        LayoutInflater.from(context).inflate(R.layout.stand_layout, this, true)

        val standServicesGroup = findViewById<ChipGroup>(R.id.stand_chipGroup)

        for (service: Service in stand.services) {
            val serviceChip: Chip = Chip(context)
            serviceChip.text = service.libelle
            standServicesGroup?.addView(serviceChip)
        }

    }
}