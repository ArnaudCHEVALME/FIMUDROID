package com.fimu.app.ui.artistes

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fimu.app.R
import com.fimu.app.network.models.Artiste
import com.fimu.app.util.OnItemClickListener
import com.squareup.picasso.Picasso
import java.io.ByteArrayOutputStream

class ArtistAdapter(
    private var dataset: List<Artiste>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<ArtistAdapter.ItemViewHolder>() {

    private lateinit var savedSearch: String

    class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.artist_name_text_view)
        val imageView: ImageView = view.findViewById(R.id.imageButton)
        val pastille: View = view.findViewById(R.id.pastille_genre)
        val category: TextView = view.findViewById(R.id.categorie_text_view)
        val pays: TextView = view.findViewById(R.id.paysTextView)
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
        Log.d("Photo", artiste.id.toString() + ":" + artiste.nom.toString())


        val imageResId: Int // Identifiant de ressource de l'image dans le dossier drawable

        when (artiste.id) {
            150 -> imageResId = R.drawable.img_a_cuatro_voces
            171 -> imageResId = R.drawable.img_academic_choir_of_nicolaus_copernicus_university_in_torun
            106 -> imageResId = R.drawable.img_akatsuki_sn
            107 -> imageResId = R.drawable.img_akira_noface
            172 -> imageResId = R.drawable.img_alan_wongsoloist
            108 -> imageResId = R.drawable.img_awori
            149 -> imageResId = R.drawable.img_aagut
            109 -> imageResId = R.drawable.img_balqeis_live
            173 -> imageResId = R.drawable.img_banda_sinfonica_universitat_politecnicade_valencia
            138 -> imageResId = R.drawable.img_benditorchestra
            139 -> imageResId = R.drawable.img_big_band_utbm
            110 -> imageResId = R.drawable.img_blue_pulpit
            151 -> imageResId = R.drawable.img_burkina_azza
            152 -> imageResId = R.drawable.img_camleo
            112 -> imageResId = R.drawable.img_cloud
            174 -> imageResId = R.drawable.img_chloe_clergetleclerc
            178 -> imageResId = R.drawable.img_choeur_universitaire_de_nancy
            176 -> imageResId = R.drawable.img_choeurdechambredeluniversitedestrasbourg
            //177 -> imageResId = R.drawable.img_choeur_du_coge
            175 -> imageResId = R.drawable.img_clash_trio
            111 -> imageResId = R.drawable.img_cleaver
            113 -> imageResId = R.drawable.img_damantra
            179 -> imageResId = R.drawable.img_dis_duo
            153 -> imageResId = R.drawable.img_dicko_fils
            180 -> imageResId = R.drawable.img_duo_motus
            140 -> imageResId = R.drawable.img_el_dor_el_awal
            114 -> imageResId = R.drawable.img_emile_bilodeau
            115 -> imageResId = R.drawable.img_encore

            181 -> imageResId = R.drawable.img_ensamble_de_percusiones_de_la_universidad_autonoma_de_zacatecas
            //182 -> imageResId = R.drawable.img_ensemble_d_accordeons_du_crddebelfort
            183 -> imageResId = R.drawable.img_filarmonica_di_belfiore
            116 -> imageResId = R.drawable.img_flupke
            141 -> imageResId = R.drawable.img_fulu
            //184 -> imageResId = R.drawable.img_fulu
            117 -> imageResId = R.drawable.img_grayssoker
            154 -> imageResId = R.drawable.img_hawasband
            118 -> imageResId = R.drawable.img_hippiehourrah
            119 -> imageResId = R.drawable.img_hoorsees
            142 -> imageResId = R.drawable.img_hugodiaz_quartet
            120 -> imageResId = R.drawable.img_jjhpotter
            121 -> imageResId = R.drawable.img_kanen
            155 -> imageResId = R.drawable.img_labandedeviolondefranchecomte
            185 -> imageResId = R.drawable.img_lachambresymphonique
            186 -> imageResId = R.drawable.img_larajokhadar
            157 -> imageResId = R.drawable.img_lasbaklavas
            156 -> imageResId = R.drawable.img_laspalomitasserranas
            122 -> imageResId = R.drawable.img_lauraniquay
            143 -> imageResId = R.drawable.img_leogellertrio
            //187 -> imageResId = R.drawable.img_lesgrandestraversees
            188 -> imageResId = R.drawable.img_lowcostbrassband
            158 -> imageResId = R.drawable.img_mamagodillot
            160 -> imageResId = R.drawable.img_modkozmik
            123 -> imageResId = R.drawable.img_morrojent
            159 -> imageResId = R.drawable.img_musicalsouvenir

            124 -> imageResId = R.drawable.img_mystically
            125 -> imageResId = R.drawable.img_nastyjoe
            //126 -> imageResId = R.drawable.img_nathanvanheuverzwijn
            128 -> imageResId = R.drawable.img_oni
            127 -> imageResId = R.drawable.img_oceya
            189 -> imageResId = R.drawable.img_orchestre_oblique
            191 -> imageResId = R.drawable.img_orchestre_d_harmonie_de_turckheim
            //190 -> imageResId = R.drawable.img_orchestreetchoeurbaroqueducrddebelfort
            129 -> imageResId = R.drawable.img_pales
            161 -> imageResId = R.drawable.img_pahua
            //192 -> imageResId = R.drawable.img_philarmonieducoge
            130 -> imageResId = R.drawable.img_poligone
            193 -> imageResId = R.drawable.img_quatuoriliade
            132 -> imageResId = R.drawable.img_rust
            162 -> imageResId = R.drawable.img_remna

            131 -> imageResId = R.drawable.img_romainmuller
            146 -> imageResId = R.drawable.img_selil
            //163 -> imageResId = R.drawable.img_shkaxheladinzeqiri
            194 -> imageResId = R.drawable.img_samodaiszivesduo
            133 -> imageResId = R.drawable.img_sendmeloveletters
            195 -> imageResId = R.drawable.img_sextetomistico
            196 -> imageResId = R.drawable.img_studentenorchestermunster
            134 -> imageResId = R.drawable.img_t2l
            //164 -> imageResId = R.drawable.img_tafep
            165 -> imageResId = R.drawable.img_theceltictramps
            166 -> imageResId = R.drawable.img_thechoirofemikeladzecentralmusicschool
            167 -> imageResId = R.drawable.img_thegaulwayramblers
            197 -> imageResId = R.drawable.img_thejezreelvalleycenterfortheartsstringensemble
            //168 -> imageResId = R.drawable.img_thraxpunks
            135 -> imageResId = R.drawable.img_tomrochet

            198 -> imageResId = R.drawable.img_transnationalcelloensemble
            199 -> imageResId = R.drawable.img_trio_musius
            169 -> imageResId = R.drawable.img_turbo_gumzi
            200 -> imageResId = R.drawable.img_tuttifluti
            136 -> imageResId = R.drawable.img_vapa
            137 -> imageResId = R.drawable.img_voiture
            170 -> imageResId = R.drawable.img_waagal
            147 -> imageResId = R.drawable.img_wet_enough
            201 -> imageResId = R.drawable.img_wooden_shapes
            //148 -> imageResId = R.drawable.img_yuba_afrobeat
            145 -> imageResId = R.drawable.img_na
            //144 -> imageResId = R.drawable.img_matrigal
            else -> imageResId = R.drawable.noimageplaceholder // Placeholder image when there is no matching drawable resource
        }

// Utilisez l'identifiant de ressource de l'image pour charger l'image avec Picasso
        Picasso.get()
            .load(imageResId)
            .config(Bitmap.Config.RGB_565)
            .resize(200, 200)
            .centerCrop()
            .into(holder.imageView)

        holder.itemView.setOnClickListener {
            listener.onItemClick(artiste.id)
        }

        holder.textView.text = artiste.nom
        holder.category.text = artiste.categorie.libelle
        holder.pays.text = artiste.pays?.joinToString(", ") { it.libelle }
        holder.pastille.background.setTint(Color.parseColor(artiste.categorie.couleur ?: "#000000"))
    }

    fun updateData(artistes: List<Artiste>) {
        this.dataset = artistes as List<Artiste>
        notifyDataSetChanged()
    }

    fun sortByArtistName() {
        dataset = dataset.sortedBy { it.nom }
        notifyDataSetChanged()
    }

    fun sortByArtistNameDesc() {
        dataset = dataset.sortedByDescending { it.nom }
        notifyDataSetChanged()
    }

    fun sortByArtistCategory() {
        dataset = dataset.sortedBy { it.categorie.libelle }
        notifyDataSetChanged()
    }

    fun sortByArtistCategoryDesc() {
        dataset = dataset.sortedByDescending { it.categorie.libelle }
        notifyDataSetChanged()
    }

    fun searchByArtistName(search: String) {
        savedSearch = search
        dataset = dataset.filter { it.nom.contains(savedSearch, true) }
        notifyDataSetChanged()
    }


}