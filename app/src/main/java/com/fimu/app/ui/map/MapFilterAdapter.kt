package com.fimu.app.ui.map

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Switch
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.recyclerview.widget.RecyclerView
import com.fimu.app.R
import com.fimu.app.database.FimuDB
import com.google.android.material.materialswitch.MaterialSwitch
import kotlinx.coroutines.*


class MapFilterAdapter(
    var dataset: List<com.fimu.app.network.models.TypeStand>,
    private val lifecycleScope: LifecycleCoroutineScope, // Add this parameter
) : RecyclerView.Adapter<MapFilterAdapter.ItemViewHolder>(), CoroutineScope by lifecycleScope {




    class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val typeName: MaterialSwitch = view.findViewById(R.id.icon_name)
        val typeIcon: ImageView = view.findViewById(R.id.icon_image)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {

        return ItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.bottom_sheet_map_filter_item, parent, false)
        )


    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

        val typeStandDao = FimuDB.getInstance(holder.itemView.context).typeStandDao()

        val typeStand = dataset[position]
        val drawableResource = when (typeStand.libelle) {
            "Bar à eau" -> R.drawable.map_eau
            "Boutique" -> R.drawable.map_boutique
            "Buvette" -> R.drawable.map_buvette
            "Entrées" -> R.drawable.map_entree
            "FIMU des Enfants" -> R.drawable.map_enfant
            "Navette" -> R.drawable.map_navette
            "Parking vélos" -> R.drawable.map_parking_velo
            "Partenaire" -> R.drawable.map_stand
            "Point Infos" -> R.drawable.map_info
            "Prévention" -> R.drawable.map_prevention
            "Secours" -> R.drawable.map_secours
            "Stand alimentaire" -> R.drawable.map_resto
            "Toilettes" -> R.drawable.map_toilet
            else -> {
                R.drawable.map_marker
            }
        }
        holder.typeIcon.setImageResource(drawableResource)
        holder.typeName.text = typeStand.libelle



        GlobalScope.launch(Dispatchers.Main) {
            holder.typeName.isChecked = withContext(Dispatchers.IO) {
                typeStandDao.isShowed(typeStand.libelle)
            }

            holder.typeName.setOnCheckedChangeListener { _, isChecked ->
                GlobalScope.launch(Dispatchers.IO) {
                    typeStandDao.updateShowed(typeStand.libelle, isChecked)
                    Log.d("MapFilterAdapter", "typeStandDao.getAll(): ${typeStandDao.getAll()}")
                }
            }

        }
    }

    override fun getItemCount(): Int {
        return dataset.size
    }
}






