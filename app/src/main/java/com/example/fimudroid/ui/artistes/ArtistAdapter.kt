package com.example.fimudroid.ui.artistes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fimudroid.R
import com.example.fimudroid.network.models.Artiste

class ArtistAdapter(
    private val dataset: List<Artiste>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<ArtistAdapter.ItemViewHolder>() {

    class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.artist_name_text_view)
        val imageView: ImageView = view.findViewById(R.id.imageButton)
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

        val drawableResource = when (artiste.id) {
            1 -> R.drawable.ma_joye
            2 -> R.drawable.ma_pales
            3 -> R.drawable.ma_grayssoker
            4 -> R.drawable.ma_nastyjoe
            5 -> R.drawable.ma_poligone
            6 -> R.drawable.ma_romainmuller
            7 -> R.drawable.ma_tomrochet
            8 -> R.drawable.ma_oceya
            9 -> R.drawable.ma_encore
            10 -> R.drawable.ma_encore
            else -> R.drawable.ma_cloud // Placeholder image when there is no matching drawable resource
        }

        holder.imageView.setImageResource(drawableResource)

        holder.itemView.setOnClickListener{
            listener.onItemClick(artiste.id)
        }

        holder.textView.text = artiste.nom

        val categoryTextView = holder.itemView.findViewById<TextView>(R.id.categorie_text_view)
        categoryTextView.text = artiste.categorie.libelle

        val genreView = holder.itemView.findViewById<LinearLayout>(R.id.genre_view)

        for (genre in artiste.genres) {
            val txt = TextView(genreView.context)
            txt.text = genre.libelle
            genreView.addView(txt)
        }

        val paysView = holder.itemView.findViewById<LinearLayout>(R.id.pays_view)

        for (pays in artiste.pays!!) {
            val txt = TextView(paysView.context)
            txt.text = pays.libelle
            paysView.addView(txt)
        }
    }

}