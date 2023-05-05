package com.example.fimudroid.ui.map

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.fimudroid.R
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

        lifecycleScope.launch {
            var typesStand = withContext(Dispatchers.IO) {
                api.getTypesStand().data
            }.sortedBy { it.libelle }
            val typeStandAdapter = MapFilterAdapter(typesStand)
            recyclerView.adapter = typeStandAdapter
            recyclerView.addItemDecoration(
                DividerItemDecoration(
                    recyclerView.context, DividerItemDecoration.VERTICAL
                )
            )
            view.findViewById<RecyclerView>(R.id.map_filter_recycler_view).setHasFixedSize(true)
        }
        return bottomSheet
    }
}
