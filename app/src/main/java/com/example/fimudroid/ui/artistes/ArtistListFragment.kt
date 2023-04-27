package com.example.fimudroid.ui.artistes

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ToggleButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.fimudroid.R
import com.example.fimudroid.database.FimuDB
import com.example.fimudroid.network.FimuApiService
import com.example.fimudroid.network.models.Artiste
import com.example.fimudroid.network.retrofit
import com.example.fimudroid.util.OnItemClickListener
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ArtistListFragment : Fragment(), OnItemClickListener {
    private lateinit var db: FimuDB
    private val api: FimuApiService by lazy {
        retrofit.create(FimuApiService::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.artist_recycler, container, false)

        val recyclerView: RecyclerView = root.findViewById(R.id.artist_recycler_view)

        // Use a coroutine to fetch the API data and insert it into the DAO
        lifecycleScope.launch {
            var artistes: List<Artiste> = withContext(Dispatchers.IO) {
                api.getArtistes().data
            }.sortedBy { it.nom }

            Log.i("news", artistes.toString())

            // Display the data in a RecyclerView
            val artistAdapter = ArtistAdapter(artistes, this@ArtistListFragment)

            recyclerView.adapter = artistAdapter
            recyclerView.addItemDecoration(
                DividerItemDecoration(
                    recyclerView.context, DividerItemDecoration.VERTICAL
                )
            )
            root.findViewById<RecyclerView>(R.id.artist_recycler_view).setHasFixedSize(true)

            val sortAZButton: MaterialButton = root.findViewById(R.id.artist_sortAZ_button)
            sortAZButton.isChecked = true
            sortAZButton.setOnClickListener {
                if (sortAZButton.tag == "asc") {
                    sortAZButton.tag = "desc"
                    artistes = artistes.sortedByDescending { it.nom }
                    sortAZButton.text = "Z - A"
                } else {
                    sortAZButton.tag = "asc"
                    artistes = artistes.sortedBy { it.nom }
                    sortAZButton.text = "A - Z"
                }
                artistAdapter.updateData(artistes)
                artistAdapter.notifyDataSetChanged()
            }

            val sortCatButton = root.findViewById<View>(R.id.artist_sortCat_button)
            sortCatButton.setOnClickListener {
                if (sortCatButton.tag == "asc") {
                    sortCatButton.tag = "desc"
                    artistes = artistes.sortedByDescending { it.categorie.libelle }
                } else {
                    sortCatButton.tag = "asc"
                    artistes = artistes.sortedBy { it.categorie.libelle }
                }
                artistAdapter.updateData(artistes)
                artistAdapter.notifyDataSetChanged()
            }
        }
        return root
    }

    override fun onItemClick(itemId: Int) {
        val bundle = Bundle()
        bundle.putInt("id_art", itemId)
        requireView().findNavController()
            .navigate(R.id.action_navigation_artiste_list_to_artisteDetailsFragment, bundle)
    }
}
