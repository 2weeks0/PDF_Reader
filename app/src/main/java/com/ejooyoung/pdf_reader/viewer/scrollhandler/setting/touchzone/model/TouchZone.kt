package com.ejooyoung.pdf_reader.viewer.scrollhandler.setting.touchzone.model

import androidx.databinding.ObservableInt

data class TouchZone(
    val widthProgress: ObservableInt = ObservableInt(),
    val heightProgress: ObservableInt = ObservableInt(),
    val marginProgress: ObservableInt = ObservableInt()
) {
    companion object {
        fun of(
            widthProgress: Int = TouchZonePreference.WIDTH_PROGRESS.defValue,
            heightProgress: Int = TouchZonePreference.HEIGHT_PROGRESS.defValue,
            marginProgress: Int = TouchZonePreference.MARGIN_PROGRESS.defValue
        ): TouchZone {
            return TouchZone().apply {
                set(widthProgress, heightProgress, marginProgress)
            }
        }
    }

    fun set(
        widthProgress: Int = -1,
        heightProgress: Int = -1,
        marginProgress: Int = -1
    ) {
        if (widthProgress >= 0) {
            this.widthProgress.set(if (widthProgress == 0) 1 else widthProgress)
        }
        if (heightProgress >= 0) {
            this.heightProgress.set(if (heightProgress == 0) 1 else heightProgress)
        }
        if (marginProgress >= 0) {
            this.marginProgress.set(marginProgress)
        }
    }
}