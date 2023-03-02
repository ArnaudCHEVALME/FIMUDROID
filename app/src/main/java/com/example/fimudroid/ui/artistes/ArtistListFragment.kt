package com.example.fimudroid.ui.artistes

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.recyclerview.widget.RecyclerView
import com.example.fimudroid.R
import com.example.fimudroid.adapter.ItemArtistAdapter
import com.example.fimudroid.databinding.ActivityMainBinding
import com.example.fimudroid.data.DataSource

class ArtistListFragment : Fragment() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.artist_list_layout, container, false)

        // Initialize data.
        val myDataset = DataSource().loadArtists()

        Log.d("TAG", myDataset.toString())

        val recyclerView = view.findViewById<RecyclerView>(R.id.artist_recycler_view)

        Log.d("TAG", recyclerView.toString())

        recyclerView.adapter = ItemArtistAdapter(requireContext(), myDataset)

        // Use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true)

        return view
    }
    private fun <T> findViewById(recyclerView: Int): Any {
        TODO("Not yet implemented")
    }
}