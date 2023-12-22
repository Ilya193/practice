package com.example.studying

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class StartGameDialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(context)
        .setMessage("Start game")
            .setPositiveButton("Start") { dialog, id ->
            }
            .setNegativeButton("Cancel") { dialog, id ->
            }
        .create()
    }
}