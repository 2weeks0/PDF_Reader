package com.ejooyoung.pdf_reader.base.dialog

import android.app.Dialog
import android.content.Context
import com.ejooyoung.pdf_reader.R
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.layout_input_text_dialog.*

class InputTextDialogFactory(
    context: Context,
    styleId: Int = R.style.InputTextDialog
) {

    private val dialog: BottomSheetDialog = BottomSheetDialog(context, styleId).apply {
        setContentView(R.layout.layout_input_text_dialog)
        tvCancel.setOnClickListener { this.dismiss() }
        edt.requestFocus()
    }

    fun setTitle(stringId: Int): InputTextDialogFactory {
        dialog.tvTitle.setText(stringId)
        return this
    }

    fun setInitialText(string: String): InputTextDialogFactory {
        with(dialog.edt) {
            setText(string)
            setSelection(string.length)
        }
        return this
    }

    fun setConfirmClickListener(listener: (dialog: Dialog, string: String) -> Unit): InputTextDialogFactory {
        with(dialog) {
            tvConfirm.setOnClickListener {
                listener.invoke(this, edt.text.toString())
            }
        }
        return this
    }

    fun show() {
        dialog.show()
    }
}