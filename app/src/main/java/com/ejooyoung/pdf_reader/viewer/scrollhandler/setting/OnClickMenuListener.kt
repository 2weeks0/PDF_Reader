package com.ejooyoung.pdf_reader.viewer.scrollhandler.setting

import android.view.View
import com.ejooyoung.pdf_reader.application.preference.ViewerPreference

interface OnClickMenuListener {
    fun onClickMenu(view: View, viewerPreference: ViewerPreference)
}