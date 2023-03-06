package com.example.fimudroid.ui.artistes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.room.util.EMPTY_STRING_ARRAY
import com.example.fimudroid.R
import com.example.fimudroid.adapter.ArtistAdapter

class ArtistListFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.artist_recycler, container, false)
        val recyclerView: RecyclerView = root.findViewById(R.id.artist_recycler_view)
        recyclerView.adapter = ArtistAdapter(emptyList())

        val viewModel = ViewModelProvider(
            this, ViewModelProvider.AndroidViewModelFactory(requireActivity().application)
        )[ArtistViewModel::class.java]

        viewModel.getAllArtists().observe(viewLifecycleOwner) { artists ->
            // update UI with list of artists
            recyclerView.adapter = ArtistAdapter(artists)
        }

        viewModel.getAllArtists()

        root.findViewById<RecyclerView>(R.id.artist_recycler_view).setHasFixedSize(true)
        return root
    }

}