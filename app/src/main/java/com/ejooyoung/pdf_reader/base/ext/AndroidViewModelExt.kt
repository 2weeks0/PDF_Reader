package com.ejooyoung.pdf_reader.base.ext

import android.widget.Toast
import androidx.lifecycle.AndroidViewModel

fun AndroidViewModel.makeToast(stringId: Int) {
    Toast.makeText(getApplication(), stringId, Toast.LENGTH_SHORT).show()
}