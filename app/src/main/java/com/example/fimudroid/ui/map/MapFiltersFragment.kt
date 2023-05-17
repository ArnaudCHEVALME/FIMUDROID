package com.example.fimudroid.ui.map

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.fimudroid.R
import com.example.fimudroid.database.FimuDB
import com.example.fimudroid.network.FimuApiService
import com.example.fimudroid.network.retrofit
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MapFiltersFragment : BottomSheetDialogFragment() {
    private val api: FimuApiService by lazy {
        retrofit.create(FimuApiService::class.java)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheet = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        val view = layoutInflater.inflate(R.layout.map_filters_fragment, null)
        bottomSheet.setContentView(view)
        val recyclerView: RecyclerView = view.findViewById(R.id.map_filter_recycler_view)
        var typesStand = listOf<com.example.fimudroid.network.models.TypeStand>()

        lifecycleScope.launch {
            if (FimuDB.getInstance(requireContext()).typeStandDao().getAll().isEmpty()) {
                Log.d("MapFiltersFragment", "FimuDB is empty")
                typesStand = withContext(Dispatchers.IO) {
                    api.getTypesStand().data
                }.sortedBy { it.libelle }

                // Set showed property to true for the first time
                typesStand.forEach { typeStand ->
                    typeStand.showed = true
                }

                // Save the updated typesStand to the database
                FimuDB.getInstance(requireContext()).typeStandDao().insertAll(typesStand.map {
                    com.example.fimudroid.database.models.TypeStand(
                        it.id,
                        it.libelle,
                        true
                    )
                })
            } else {
                Log.d("MapFiltersFragment", "FimuDB is not empty")
                typesStand = FimuDB.getInstance(requireContext()).typeStandDao().getAll()
                    .map {
                        com.example.fimudroid.network.models.TypeStand(
                            it.id,
                            it.libelle
                        )
                    }
            }

            val typeStandAdapter = MapFilterAdapter(typesStand, lifecycleScope)

            Log.d("MapFiltersFragment", "typesStand: $typesStand")

            recyclerView.adapter = typeStandAdapter
            recyclerView.addItemDecoration(
                DividerItemDecoration(
                    recyclerView.context, DividerItemDecoration.VERTICAL
                )
            )
        }
        recyclerView.setHasFixedSize(true)
        return bottomSheet
    }

    override fun onStop() {
        super.onStop()
        updateMarkers()
    }

    private fun updateMarkers() {
        val mapFragment =
            FragmentManager.findFragment(requireActivity().findViewById(R.id.mapView)) as MapFragment
        mapFragment.updateMap()
    }
}

