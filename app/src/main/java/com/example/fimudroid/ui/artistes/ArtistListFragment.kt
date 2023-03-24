package com.example.fimudroid.ui.artistes

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.fimudroid.R
import com.example.fimudroid.network.FimuApiService
import com.example.fimudroid.network.models.Artiste
import com.example.fimudroid.network.retrofit
import com.example.fimudroid.util.OnItemClickListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ArtistListFragment : Fragment(), OnItemClickListener {

    private val api: FimuApiService by lazy {
        retrofit.create(FimuApiService::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.artist_recycler, container, false)

        val recyclerView: RecyclerView = root.findViewById(R.id.artist_recycler_view)

        lifecycleScope.launch {
            val artistes: List<Artiste> = withContext(Dispatchers.IO) {
                api.getArtistes().data
            }

            Log.i("news", artistes.toString())

            val artistAdapter = ArtistAdapter(artistes, this@ArtistListFragment)

            recyclerView.adapter = artistAdapter
            recyclerView.addItemDecoration(
                DividerItemDecoration(
                    recyclerView.context, DividerItemDecoration.VERTICAL
                )
            )
            root.findViewById<RecyclerView>(R.id.artist_recycler_view).setHasFixedSize(true)
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