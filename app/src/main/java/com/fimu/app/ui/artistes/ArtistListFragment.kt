package com.fimu.app.ui.artistes

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.fimu.app.R
import com.fimu.app.database.FimuDB
import com.fimu.app.network.FimuApiService
import com.fimu.app.network.models.Artiste as NetworkArtiste
import com.fimu.app.database.models.Artiste as DatabaseArtiste
import com.fimu.app.network.models.Concert as NetworkConcert
import com.fimu.app.database.models.Concert as DatabaseConcert
import com.fimu.app.network.retrofit
import com.fimu.app.util.OnItemClickListener
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ArtistListFragment : Fragment(), OnItemClickListener, ArtistFilterListener {
    private lateinit var db: FimuDB
    private val api: FimuApiService by lazy {
        retrofit.create(FimuApiService::class.java)
    }

    private lateinit var artistFiltersFragment: ArtistFiltersFragment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.artist_recycler, container, false)
        val recyclerView: RecyclerView = root.findViewById(R.id.artist_recycler_view)

        val artisteAdapter = ArtistAdapter(emptyList(), this@ArtistListFragment)
        recyclerView.adapter = artisteAdapter
        recyclerView.addItemDecoration(
            DividerItemDecoration(recyclerView.context, DividerItemDecoration.VERTICAL)
        )
        recyclerView.setHasFixedSize(true)

        val sortAZButton: MaterialButton = root.findViewById(R.id.artist_sortAZ_button)
        sortAZButton.isChecked = true
        sortAZButton.tag = "asc"
        sortAZButton.setOnClickListener {
            if (sortAZButton.tag == "asc") {
                artisteAdapter.sortByArtistNameDesc()
                sortAZButton.tag = "desc"
                sortAZButton.text = "Z - A"
            } else {
                artisteAdapter.sortByArtistName()
                sortAZButton.tag = "asc"
                sortAZButton.text = "A - Z"
            }
        }

        val sortCatButton: MaterialButton = root.findViewById(R.id.artist_sortCat_button)
        sortCatButton.setOnClickListener {
            if (sortCatButton.tag == "asc") {
                artisteAdapter.sortByArtistCategoryDesc()
                sortCatButton.tag = "desc"
            } else {
                artisteAdapter.sortByArtistCategory()
                sortCatButton.tag = "asc"
            }
        }


        artistFiltersFragment = ArtistFiltersFragment()


        lifecycleScope.launch {
            if (requireContext().fileList().contains("artistes.json")) {
                val artistes = readArtistsFromJsonFile()
                artisteAdapter.updateData(artistes)
                artisteAdapter.sortByArtistName()
                sortAZButton.isChecked = true
                sortAZButton.tag = "asc"
                sortAZButton.text = "A - Z"
            } else {
                try {
                    val response = api.getArtistes().data
                    val jsonResponse = Gson().toJson(response)
                    // save the response to a file
                    requireContext().openFileOutput("artistes.json", MODE_PRIVATE).use {
                        it.write(jsonResponse.toByteArray())
                    }

                    val artistes = readArtistsFromJsonFile()
                    artisteAdapter.updateData(artistes)
                    artisteAdapter.sortByArtistName()
                    sortAZButton.isChecked = true
                    sortAZButton.tag = "asc"
                    sortAZButton.text = "A - Z"
                } catch (e: Exception) {
                    Log.e("ArtistListFragment", "Error fetching artistes", e)
                }
            }
        }

        return root
    }

    private fun readArtistsFromJsonFile(): List<NetworkArtiste> {
        val fileInputStream = requireContext().openFileInput("artistes.json")
        val jsonString = fileInputStream.bufferedReader().use { it.readText() }
        val gson = Gson()
        val artistes = gson.fromJson(jsonString, Array<NetworkArtiste>::class.java)
        return artistes.toList()
    }

    override fun onItemClick(itemId: Int) {
        val bundle = Bundle()
        bundle.putInt("id_art", itemId)
        requireView().findNavController()
            .navigate(R.id.action_navigation_artiste_list_to_artisteDetailsFragment, bundle)
    }

    override fun onSearchByArtistName(search: String) {
        // Handle the search action here
        // Update the adapter or perform any required operations based on the search input
        Log.d("ArtistListFragment", "Search by artist name: $search")
    }
}
