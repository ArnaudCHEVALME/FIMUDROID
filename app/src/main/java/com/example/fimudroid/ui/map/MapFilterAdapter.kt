package com.example.fimudroid.ui.map

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fimudroid.R
import com.example.fimudroid.database.models.Stand
import com.example.fimudroid.network.models.TypeStand

class MapFilterAdapter(
    private var dataset: List<TypeStand>
) :
    RecyclerView.Adapter<MapFilterAdapter.ItemViewHolder>() {

    class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val typeName: TextView = view.findViewById(R.id.icon_name)
        val typeIcon: ImageView = view.findViewById(R.id.icon_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.bottom_sheet_map_filter_item, parent, false))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val typeStands = dataset[position]
        val drawableResource = when (typeStands.id) {
            1 -> R.drawable.map_resto
            2 -> R.drawable.map_resto
            3 -> R.drawable.map_secours
            4 -> R.drawable.map_buvette
            5 -> R.drawable.map_boutique
            6 -> R.drawable.map_toilet
            7 -> R.drawable.map_eau
            8 -> R.drawable.ma_oceya
            9 -> R.drawable.ma_encore
            10 -> R.drawable.ma_encore
            else -> R.drawable.ma_cloud
        }

        holder.typeIcon.setImageResource(drawableResource)
        holder.typeName.text = typeStands.libelle


        Log.i("ALLLLAED", "onBindViewHolder: ${typeStands.libelle}")
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    fun updateData(typeStands: List<TypeStand>) {
        this.dataset = typeStands
        notifyDataSetChanged()
    }
}


