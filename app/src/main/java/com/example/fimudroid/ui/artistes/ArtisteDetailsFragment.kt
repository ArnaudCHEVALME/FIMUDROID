package com.example.fimudroid.ui.artistes

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.fimudroid.R


class ArtisteDetailsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val artiste_id:Int = arguments?.getInt("id_art") ?: -1
        Log.i("LES ARTISTE PUTAIN CHAR TU VAS LES BOUFFER LA MERDE", artiste_id.toString())
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.artiste_details_fragment, container, false)
    }



}