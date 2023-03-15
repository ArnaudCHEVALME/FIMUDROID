package com.example.fimudroid.ui.artistes

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.fimudroid.R
import com.example.fimudroid.database.models.Artiste
import com.example.fimudroid.ui.news.NewsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ArtisteDetailsFragment() : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viewModel = ViewModelProvider(
            this, ViewModelProvider.AndroidViewModelFactory(requireActivity().application)
        )[ArtistViewModel::class.java]

        // Inflate the layout for this fragment7
        val root = inflater.inflate(R.layout.artiste_details_fragment, container, false)
        val artiste_id:Int = arguments?.getInt("id_art") ?: -1
        lifecycleScope.launch(Dispatchers.IO) {
            val currentArtiste = viewModel.getById(artiste_id)

            val groupe: TextView = root.findViewById(R.id.nomGroupe)
            val genreGroupe: TextView = root.findViewById(R.id.textView5)
            val paysOrigine: TextView = root.findViewById(R.id.textView6)
            val description: TextView = root.findViewById(R.id.textView7)
            // val favori
            // val lienRéseau
            val programme: TextView = root.findViewById(R.id.textView)
            // val video
            // val logoGroupe
            val horaire1: TextView = root.findViewById(R.id.horrairePassage)
            val horaire2: TextView = root.findViewById(R.id.secondeHorraire)
            groupe.text=currentArtiste[0].nom
            description.text=currentArtiste[0].biographie
        }


        return root
    }

}