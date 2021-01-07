package com.ejooyoung.pdf_reader.viewer.scrollhandler.grid

import android.view.View

interface GridViewerClickListener {
    fun onClickGridViewer(view: View, pageIdx: Int)
}