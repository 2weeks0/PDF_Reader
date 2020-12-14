package com.ejooyoung.pdf_reader.viewer.scrollhandler.contents

import com.ejooyoung.pdf_reader.database.model.Contents

interface ContentsClickListener {
    fun onClickContents(pageIdx: Int)
    fun onLongClickContents(contents: Contents): Boolean
}