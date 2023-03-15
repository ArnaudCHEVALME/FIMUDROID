package com.example.fimudroid.ui.artistes

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.MediaController
import android.widget.TextView
import android.widget.VideoView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.fimudroid.R
import com.example.fimudroid.network.FimuApiService
import com.example.fimudroid.network.retrofit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ArtisteDetailsFragment() : Fragment() {

    private val api: FimuApiService by lazy {
        retrofit.create(FimuApiService::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment7
        val root = inflater.inflate(R.layout.artiste_details_fragment, container, false)
        val artiste_id: Int = arguments?.getInt("id_art") ?: -1
        lifecycleScope.launch(Dispatchers.IO) {
            val currentArtiste = api.getArtisteById(artiste_id).data

            withContext(Dispatchers.Main) {
                val groupe: TextView = root.findViewById(R.id.nomGroupe)
                val genreGroupe: TextView = root.findViewById(R.id.textView5)
                var allGenre =""
                for (genre in currentArtiste.genres!!){
                    allGenre += genre.libelle.toString()
                    allGenre += ", "
                }
                genreGroupe.text = allGenre

                val paysOrigine: TextView = root.findViewById(R.id.textView6)
                var result: String = ""
                for (pays in currentArtiste.pays!!){
                    result += pays.libelle.toString()
                    result += ", "
                }
                paysOrigine.text = result


                // val videoView = root.findViewById<VideoView>(R.id.artisteVideo);
                // val uri = Uri.parse(currentArtiste.lien_video)
                // videoView.setVideoURI(uri)

                // val mediaController = MediaController(this@ArtisteDetailsFragment.requireContext())

                // sets the anchor view
                // anchor view for the videoView

                // sets the anchor view
                // anchor view for the videoView
                // mediaController.setAnchorView(videoView)

                // sets the media player to the videoView

                // sets the media player to the videoView
                // mediaController.setMediaPlayer(videoView)

                // sets the media controller to the videoView

                // sets the media controller to the videoView
                // videoView.setMediaController(mediaController)

                // starts the video

                // starts the video
                // videoView.start()



                val description: TextView = root.findViewById(R.id.textView7)
                // val lienRéseau
                val programme: TextView = root.findViewById(R.id.textView)
                // val video
                // val logoGroupe
                val horaires: TextView = root.findViewById(R.id.horrairePassage)
                var horaireArtiste =""
                for (horaire in currentArtiste.concerts){
                    horaireArtiste += horaire.date_debut +" à "+ horaire.heure_debut + "\n"
                }
                horaires.text = horaireArtiste


                groupe.text = currentArtiste.nom
                description.text = currentArtiste.biographie
                R.drawable.fimuapp

                val lien1: ImageButton = root.findViewById(R.id.lienButton1)
                val url = lien1.contentDescription.toString()
                lien1.setOnClickListener {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse(url)
                    startActivity(intent)
                }
                val lien2: ImageButton = root.findViewById(R.id.imageButton3)
                val url2 = lien2.contentDescription.toString()
                lien2.setOnClickListener {
                    val intent2 = Intent(Intent.ACTION_VIEW)
                    intent2.data = Uri.parse(url2)
                    startActivity(intent2)
                }
            }
        }

        return root
    }

}