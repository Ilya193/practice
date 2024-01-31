package com.example.studying

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class WarningDialogFragment : DialogFragment() {

    private lateinit var warningListener: WarningListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is WarningListener) warningListener = context
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(context)
            .setMessage("Вы уверены, что хотите выйти? Данные не будут сохранены")
            .setPositiveButton("Выйти") { dialog, id ->
                dismiss()
                warningListener.exit()
            }
            .setNegativeButton("Продолжить ввод данных") { dialog, id ->
                dismiss()
            }.create()
    }

    companion object {
        fun newInstance() = WarningDialogFragment()
    }
}