package com.example.fimudroid.ui.artistes

import android.content.Intent
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
import com.example.fimudroid.R
import com.example.fimudroid.network.FimuApiService
import com.example.fimudroid.network.retrofit
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

                val drawableResource = when (currentArtiste.id) {
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
                photoGroupeView.setImageResource(drawableResource)

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