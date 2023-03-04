package com.example.fimudroid.adapter

import android.content.Context
import android.graphics.*
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.PathShape
import android.graphics.drawable.shapes.Shape
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fimudroid.R
import com.example.fimudroid.models.Artiste
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

class ItemArtistAdapter(
    private val context: Context,
    private val dataset: List<Artiste>
) : RecyclerView.Adapter<ItemArtistAdapter.ItemViewHolder>() {


    class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.artist_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        // create a new view
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.artist_recycler_item, parent, false)

        return ItemViewHolder(adapterLayout)
    }


    override fun getItemCount() = dataset.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val artiste = dataset[position]
        holder.textView.text = artiste.nom

        val categoryTextView = holder.itemView.findViewById<TextView>(R.id.category_text_field)
        categoryTextView.text = artiste.category.libelle

        // Convert the hex color to a Color object
        val color = Color.parseColor(artiste.category.couleur)

        categoryTextView.setBackgroundColor(color)

        val genresChipGroup = holder.itemView.findViewById<ChipGroup>(R.id.genres_chip_group)
        genresChipGroup.removeAllViews()

        for (genre in artiste.genres) {
            val chip = Chip(genresChipGroup.context)
            chip.text = genre.libelle
            chip.isCheckable = false
            chip.isClickable = false
            genresChipGroup.addView(chip)
        }

        val paysChipGroup = holder.itemView.findViewById<ChipGroup>(R.id.pays_chip_group)
        paysChipGroup.removeAllViews()

        for (pays in artiste.pays!!) {
            val chip = Chip(paysChipGroup.context)
            chip.text = pays.libelle
            chip.isCheckable = false
            chip.isClickable = false
            paysChipGroup.addView(chip)
        }
    }

}