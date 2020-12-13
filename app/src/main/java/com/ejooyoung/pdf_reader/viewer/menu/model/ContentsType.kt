package com.ejooyoung.pdf_reader.viewer.menu.model

enum class ContentsType(
    val type: Int
) {
    CONTENT(0),
    BOOKMARK(1);

    companion object {
        fun valueOf(type: Int) = values()[type]
        fun size() = values().size
    }
}