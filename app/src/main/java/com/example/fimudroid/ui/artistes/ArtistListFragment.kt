package com.example.fimudroid.ui.artistes

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.fimudroid.R
import com.example.fimudroid.ui.news.ImageHeaderNewsAdapter
import com.example.fimudroid.ui.news.OnItemClickListener


interface OnItemClickListener {
    fun onItemClick(itemId: Int)
}

class ArtistListFragment : Fragment(), OnItemClickListener{
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.artist_recycler, container, false)
        val recyclerView: RecyclerView = root.findViewById(R.id.artist_recycler_view)

        val viewModel = ViewModelProvider(
            this, ViewModelProvider.AndroidViewModelFactory(requireActivity().application)
        )[ArtistViewModel::class.java]


        viewModel.getAllArtists().observe(viewLifecycleOwner) { artists ->
            val artistAdapter = ArtistAdapter(this, artists)

            recyclerView.adapter = artistAdapter
            recyclerView.addItemDecoration(
                DividerItemDecoration(
                    recyclerView.context, DividerItemDecoration.VERTICAL
                )
            )
        }









        viewModel.getAllArtists()

        root.findViewById<RecyclerView>(R.id.artist_recycler_view).setHasFixedSize(true)
        return root
    }
    override fun onItemClick(itemId: Int) {
        Log.i("CLICK", itemId.toString())
        var bundle = Bundle()
        bundle.putInt("id_news", itemId)
        requireView().findNavController().navigate(R.id.action_navigation_artiste_list_to_artisteDetailsFragment, bundle)
    }
}