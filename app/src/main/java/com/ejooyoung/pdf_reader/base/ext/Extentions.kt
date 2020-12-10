package com.ejooyoung.pdf_reader.base.ext

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun makeSnack(view: View, stringId: Int, btnStringId: Int, action: Function0<Unit>) {
    Snackbar.make(view, stringId, Snackbar.LENGTH_SHORT)
        .setAction(btnStringId) {
            action.invoke()
        }
        .show()
}