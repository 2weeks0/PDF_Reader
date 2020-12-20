package com.ejooyoung.pdf_reader.application.preference

enum class TouchZonePreference(
    val defValue: Int
){
    WIDTH_PROGRESS(50),
    HEIGHT_PROGRESS(100),
    MARGIN_PROGRESS(50),
    IS_ACTIVE(1),
    IS_HORIZONTAL(1),
    IS_LEFT_ACTION_PREVIOUS_PAGE(1),
    IS_RIGHT_ACTION_NEXT_PAGE(1);
}