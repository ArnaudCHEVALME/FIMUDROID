package com.fimu.app.ui.artistes

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.widget.doOnTextChanged
import com.fimu.app.R
import com.fimu.app.util.OnItemClickListener
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.TextInputLayout

class ArtistFiltersFragment : BottomSheetDialogFragment() {
    private lateinit var artisteAdapter: ArtistAdapter

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheet = super.onCreateDialog(savedInstanceState) as BottomSheetDialog

        val view = layoutInflater.inflate(R.layout.artist_filters_fragment, null)
        bottomSheet.setContentView(view)

        artisteAdapter = ArtistAdapter(emptyList(), object : OnItemClickListener {
            override fun onItemClick(itemId: Int) {
                val imm =
                    context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
                dismiss()
            }
        })

        var searchSavedState = arguments?.getString("searchSavedState") ?: ""


        val searchTextView: AppCompatEditText = view.findViewById(R.id.searchEditText)
        searchTextView.setText(searchSavedState)
        searchTextView.doOnTextChanged { text, _, _, _ ->
            searchSavedState = text.toString()
            ArtistFilterListener?.onSearchByArtistName(searchSavedState)

        }

        return bottomSheet
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (dialog != null && retainInstance) {
            dialog!!.setOnDismissListener(null)
        }

    }

    override fun onStop() {
        super.onStop()
    }
}

interface ArtistFilterListener {
    fun onSearchByArtistName(search: String)

    companion object {
        fun onSearchByArtistName(searchSavedState: String) {
            Log.d("ArtistFilterListener", "onSearchByArtistName: $searchSavedState")
        }
    }
}
