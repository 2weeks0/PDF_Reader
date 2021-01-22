package com.ejooyoung.pdf_reader.base.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.ArrayAdapter
import com.ejooyoung.pdf_reader.R
import com.ejooyoung.pdf_reader.base.utils.UnitUtils
import kotlinx.android.synthetic.main.layout_menu_dialog.*

class MenuDialog(
    context: Context
) : Dialog(context, R.style.MenuDialog) {

    var posX: Float = -1f
    var posY: Float = -1f
    private var listViewLayoutResourceId: Int = 0
    private var listViewItemArrayId: Int = 0
    private lateinit var listViewItemClickListener: (view: View, position: Int) -> Unit

    companion object {
        const val KEY_POS_X = "keyPosX"
        const val KEY_POS_Y = "keyPosY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_menu_dialog)
        setupPosAttributes(savedInstanceState)
        setupDialogStyle()
        setupListView()
        setOnShowListener {
            setupDialogPosition()
        }
    }

    override fun onSaveInstanceState(): Bundle {
        return super.onSaveInstanceState().apply {
            if (posX != -1f && posY != -1f) {
                putFloat(KEY_POS_X, posX)
                putFloat(KEY_POS_Y, posY)
            }
        }
    }

    private fun setupPosAttributes(savedInstanceState: Bundle?) {
        savedInstanceState?.let {
            posX = it.getFloat(KEY_POS_X)
            posY = it.getFloat(KEY_POS_Y)
        }
    }

    private fun setupDialogStyle() {
        window?.let {
            it.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            it.setGravity(Gravity.START or Gravity.TOP)
        }
    }

    private fun setupListView() {
        if (listViewLayoutResourceId == 0 || listViewItemArrayId == 0) {
            return
        }

        lv.adapter = ArrayAdapter(
            context,
            listViewLayoutResourceId,
            context.resources.getStringArray(listViewItemArrayId)
        )
        lv.setOnItemClickListener { _, view, position, _ ->
            listViewItemClickListener.invoke(view, position)
        }
    }

    private fun setupDialogPosition() {
        window?.let {
            it.attributes = it.attributes.apply {
                x = posX.toInt()
                y = posY.toInt() + UnitUtils.getNavigationHeight(this@MenuDialog.context)
            }
        }
    }

    fun setItems(
        listViewLayoutResourceId: Int,
        listViewItemArrayId: Int,
        listViewItemClickListener: (view: View, position: Int) -> Unit
    ) {
        this.listViewLayoutResourceId = listViewLayoutResourceId
        this.listViewItemArrayId = listViewItemArrayId
        this.listViewItemClickListener = listViewItemClickListener
    }

    class Factory(
        context: Context,
        private val posX: Float,
        private val posY: Float
    ) {
        private val menuDialog: MenuDialog = MenuDialog(context).apply {
            posX = this@Factory.posX
            posY = this@Factory.posY
        }

        fun setItems(
            listViewLayoutResourceId: Int,
            listViewItemArrayId: Int,
            listViewItemClickListener: (view: View, position: Int) -> Unit
        ): Factory {
            menuDialog.setItems(
                listViewLayoutResourceId,
                listViewItemArrayId,
                listViewItemClickListener
            )
            return this
        }


        fun show() {
            menuDialog.show()
        }
    }
}