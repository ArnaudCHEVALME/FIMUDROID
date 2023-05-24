package com.fimu.app.ui.artistes

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.fimu.app.R
import com.fimu.app.network.FimuApiService
import com.fimu.app.network.retrofit
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.Month


class ArtisteDetailsFragment : Fragment() {

    private val api: FimuApiService by lazy {
        retrofit.create(FimuApiService::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment7
        val root = inflater.inflate(R.layout.artiste_details_fragment, container, false)
        val artisteId: Int = arguments?.getInt("id_art") ?: -1

        lifecycleScope.launch(Dispatchers.IO) {
            val currentArtiste = api.getArtisteById(artisteId).data

            withContext(Dispatchers.Main) {
                val nomGroupeView: TextView = root.findViewById(R.id.nomGroupe)
                val genreView: TextView = root.findViewById(R.id.textView5)
                val description: TextView = root.findViewById(R.id.descriptionView)
                val photoGroupeView: ImageView = root.findViewById(R.id.imageView)
                val horaires: TextView = root.findViewById(R.id.horrairePassage)
                val paysView: TextView = root.findViewById(R.id.paysDetailTextView)

                genreView.text = currentArtiste.genres?.joinToString(", ") { it.libelle }

                paysView.text = currentArtiste.pays?.joinToString(", ") { it.libelle }

                val horaireArtiste = StringBuilder()


                for (concert in currentArtiste.concerts!!) {
                    val startDate = LocalDate.parse(concert.date_debut)
                    val jour = getDay(startDate.dayOfWeek)
                    val month = getMonth(startDate.month)

                    val heureDebut = concert.heure_debut.substringBeforeLast(":") // remove seconds
                    val heureFin = concert.heure_fin.substringBeforeLast(":") // remove seconds


                    horaireArtiste.append("$jour ${startDate.dayOfMonth} $month")
                    horaireArtiste.appendLine()
                    horaireArtiste.append("$heureDebut - $heureFin")
                    horaireArtiste.appendLine()
                    horaireArtiste.append(concert.scene.libelle)
                    horaireArtiste.append("\n\n")

                }
                horaires.text = horaireArtiste.toString().trimEnd()

                nomGroupeView.text = currentArtiste.nom
                description.text = currentArtiste.biographie

                // set links
                val linksViewGroup = root.findViewById<LinearLayout>(R.id.link_layout)

                val layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                layoutParams.setMargins(20, 20, 20, 20)

                val video_link = currentArtiste.lien_video
                if (video_link != null) {
                    val btn = ImageView(linksViewGroup.context)
//                    btn.layoutParams = layoutParams
                    btn.setImageResource(R.drawable.mdi_youtube)
                    btn.scaleType = ImageView.ScaleType.CENTER_INSIDE
                    btn.adjustViewBounds = true
                    btn.setOnClickListener {
                        val intent3 = Intent(Intent.ACTION_VIEW)
                        intent3.data = Uri.parse(video_link)
                        startActivity(intent3)
                    }
                    linksViewGroup.addView(btn)
                }


                for (link in currentArtiste.reseauxSociauxes!!) {
                    val btn = ImageView(linksViewGroup.context)
//                    btn.layoutParams = layoutParams

                    val r = when (link.logo) {
                        "mdi-facebook" -> R.drawable.mdi_facebook
                        "mdi-instagram" -> R.drawable.mdi_instagram
                        "mdi-pinterest" -> R.drawable.mdi_pinterest
                        "mdi-snapchat" -> R.drawable.mdi_snapchat
                        "mdi-spotify" -> R.drawable.mdi_spotify
                        "mdi-tiktok" -> R.drawable.mdi_tiktok
                        "mdi-twitter" -> R.drawable.mdi_twitter
                        "mdi-youtube" -> R.drawable.mdi_youtube
                        else -> R.drawable.question_mark
                    }

                    btn.setImageResource(r)
                    btn.scaleType = ImageView.ScaleType.CENTER_INSIDE
                    btn.adjustViewBounds = true
                    btn.setOnClickListener {
                        val intent3 = Intent(Intent.ACTION_VIEW)
                        intent3.data = Uri.parse(link.possede.lien)
                        startActivity(intent3)
                    }
//                    btn.cropToPadding = true
                    linksViewGroup.addView(btn)
                }

                // make linksViewGroup evenly spaced
                linksViewGroup.weightSum = linksViewGroup.childCount.toFloat()
                for (i in 0 until linksViewGroup.childCount) {
                    val child = linksViewGroup.getChildAt(i)
                    val params = child.layoutParams as LinearLayout.LayoutParams
                    params.weight = 1f
                    child.layoutParams = params
                }

/*                val drawableResource = when (currentArtiste.id) {
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
                photoGroupeView.setImageResource(drawableResource)*/

                val imageResId: Int // Identifiant de ressource de l'image dans le dossier drawable

                when (currentArtiste.id) {
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
                    .resize(600, 600)
                    .centerCrop()
                    .into(photoGroupeView)

            }
        }
        return root
    }

    private fun getDay(day : DayOfWeek) : String{
        return when (day) {
            DayOfWeek.MONDAY -> "Lundi"
            DayOfWeek.TUESDAY -> "Mardi"
            DayOfWeek.WEDNESDAY -> "Mercredi"
            DayOfWeek.THURSDAY -> "Jeudi"
            DayOfWeek.FRIDAY -> "Vendredi"
            DayOfWeek.SATURDAY -> "Samedi"
            else -> "Dimanche"
        }
    }
    private fun getMonth(month: Month) : String{
        return when (month) {
            Month.JANUARY -> "Janvier"
            Month.FEBRUARY -> "Février"
            Month.MARCH-> "Mars"
            Month.APRIL -> "Avril"
            Month.MAY -> "Mai"
            Month.JUNE -> "Juin"
            Month.JULY -> "Juillet"
            Month.AUGUST -> "Août"
            Month.SEPTEMBER -> "Séptembre"
            Month.OCTOBER -> "Octobre"
            Month.NOVEMBER -> "Novembre"
            else -> "Décembre"
        }
    }
}