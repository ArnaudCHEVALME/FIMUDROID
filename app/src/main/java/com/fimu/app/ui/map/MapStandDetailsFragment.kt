package com.fimu.app.ui.map

import android.app.Dialog
import android.os.Bundle
import android.view.View
import com.fimu.app.R
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class MapStandDetailsFragment : BottomSheetDialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheet = super.onCreateDialog(savedInstanceState) as BottomSheetDialog

        val view = layoutInflater.inflate(R.layout.map_stand_fragment, null)
        bottomSheet.setContentView(view)

        return bottomSheet
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (dialog != null && retainInstance) {
            dialog!!.setOnDismissListener(null)
        }

    }
}
