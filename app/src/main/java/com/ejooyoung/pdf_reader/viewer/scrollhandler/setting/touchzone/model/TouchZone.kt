package com.ejooyoung.pdf_reader.viewer.scrollhandler.setting.touchzone.model

import androidx.databinding.ObservableInt

data class TouchZone(
    val widthProgress: ObservableInt = ObservableInt(),
    val heightProgress: ObservableInt = ObservableInt(),
    val marginTopProgress: ObservableInt = ObservableInt()
) {
    companion object {
        fun of(
            widthProgress: Int,
            heightProgress: Int,
            marginTopProgress: Int
        ): TouchZone {
            return TouchZone().apply {
                set(widthProgress, heightProgress, marginTopProgress)
            }
        }
    }

    fun set(
        widthProgress: Int = -1,
        heightProgress: Int = -1,
        marginTopProgress: Int = -1
    ) {
        if (widthProgress >= 0) {
            this.widthProgress.set(if (widthProgress == 0) 1 else widthProgress)
        }
        if (heightProgress >= 0) {
            this.heightProgress.set(if (heightProgress == 0) 1 else heightProgress)
        }
        if (marginTopProgress >= 0) {
            this.marginTopProgress.set(if (marginTopProgress == 0) 1 else marginTopProgress)
        }
    }
}