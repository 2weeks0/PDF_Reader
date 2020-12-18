package com.ejooyoung.pdf_reader.application.preference

enum class ViewerPreference(
    val defValue: Boolean
){
    DARK_THEME(false),
    SWIPE_HORIZONTAL(true),
    FLING(true),
    FIT_WIDTH(true),
    ZOOM_BY_DOUBLE_TAP(true),
    JUMP_BY_VOLUME_KEY(true);
}