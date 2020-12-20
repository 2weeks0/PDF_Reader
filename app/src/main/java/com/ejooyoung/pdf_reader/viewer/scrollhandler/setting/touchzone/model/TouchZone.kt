package com.ejooyoung.pdf_reader.viewer.scrollhandler.setting.touchzone.model

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableInt
import com.ejooyoung.pdf_reader.application.preference.TouchZonePreference

data class TouchZone(
    val widthProgress: ObservableInt = ObservableInt(),
    val heightProgress: ObservableInt = ObservableInt(),
    val marginProgress: ObservableInt = ObservableInt(),
    val isActive: ObservableBoolean = ObservableBoolean(),
    val isHorizontal: ObservableBoolean = ObservableBoolean(),
    val isLeftActionPreviousPage: ObservableBoolean = ObservableBoolean(true),
    val isRightActionNextPage: ObservableBoolean = ObservableBoolean(true)
) {
    companion object {
        fun of(
            widthProgress: Int = TouchZonePreference.WIDTH_PROGRESS.defValue,
            heightProgress: Int = TouchZonePreference.HEIGHT_PROGRESS.defValue,
            marginProgress: Int = TouchZonePreference.MARGIN_PROGRESS.defValue,
            isActive: Int = TouchZonePreference.IS_ACTIVE.defValue,
            isHorizontal: Int = TouchZonePreference.IS_HORIZONTAL.defValue,
            isLeftActionPreviousPage: Int = TouchZonePreference.IS_LEFT_ACTION_PREVIOUS_PAGE.defValue,
            isRightActionNextPage: Int = TouchZonePreference.IS_RIGHT_ACTION_NEXT_PAGE.defValue
        ): TouchZone {
            return TouchZone().apply {
                set(
                    widthProgress,
                    heightProgress,
                    marginProgress,
                    isActive,
                    isHorizontal,
                    isLeftActionPreviousPage,
                    isRightActionNextPage
                )
            }
        }
    }

    fun set(
        widthProgress: Int = -1,
        heightProgress: Int = -1,
        marginProgress: Int = -1,
        isActive: Int = -1,
        isHorizontal: Int = -1,
        isLeftActionPreviousPage: Int = -1,
        isRightActionNextPage: Int = -1
    ) {
        if (widthProgress >= 0)
            this.widthProgress.set(if (widthProgress == 0) 1 else widthProgress)
        if (heightProgress >= 0)
            this.heightProgress.set(if (heightProgress == 0) 1 else heightProgress)
        if (marginProgress >= 0) this.marginProgress.set(marginProgress)
        if (isActive >= 0) this.isActive.set(isActive == 1)
        if (isHorizontal >= 0) this.isHorizontal.set(isHorizontal == 1)
        if (isLeftActionPreviousPage >= 0) this.isLeftActionPreviousPage.set(isLeftActionPreviousPage == 1)
        if (isRightActionNextPage >= 0) this.isRightActionNextPage.set(isRightActionNextPage == 1)
    }
}