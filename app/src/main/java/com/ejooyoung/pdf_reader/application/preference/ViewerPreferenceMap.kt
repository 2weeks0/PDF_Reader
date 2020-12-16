package com.ejooyoung.pdf_reader.application.preference

import java.util.*

class ViewerPreferenceMap private constructor(
    private val enumMap: EnumMap<ViewerPreference, Boolean>
) : MutableMap<ViewerPreference, Boolean> by enumMap {

    companion object {
        fun newInstance(): ViewerPreferenceMap {
            return ViewerPreferenceMap(EnumMap<ViewerPreference, Boolean>(ViewerPreference::class.java))
        }
    }

    override fun get(key: ViewerPreference): Boolean {
        return enumMap[key]?: key.defValue
    }
}