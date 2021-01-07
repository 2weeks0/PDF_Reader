package com.ejooyoung.pdf_reader.viewer.scrollhandler.grid

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.ejooyoung.pdf_reader.base.Const
import com.ejooyoung.pdf_reader.base.widget.ViewHolder
import com.ejooyoung.pdf_reader.databinding.ItemGridThumbnailBinding

class GridViewerAdapter(
    private val glideRequest: RequestManager,
    itemCount: Int,
    private val currentPosition: Int
) : RecyclerView.Adapter<ViewHolder>() {

    private val itemList = MutableList<Bitmap?>(itemCount) { null }
    private val clickListener = object : GridViewerClickListener {
        override fun onClickGridViewer(view: View, pageIdx: Int) {
            with(view.context as Activity) {
                val intent = Intent().apply {
                    putExtra(Const.KEY_BUNDLE_PAGE_INDEX, pageIdx)
                }
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemGridThumbnailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.binding as ItemGridThumbnailBinding) {
            glideRequest = this@GridViewerAdapter.glideRequest
            bitmap = itemList[position]
            pos = position
            isCurrentPosition = position == currentPosition
            clickListener = this@GridViewerAdapter.clickListener
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun setItem(item: Pair<Int, Bitmap>) {
        this.itemList[item.first] = item.second
        notifyDataSetChanged()
    }

    override fun onViewRecycled(holder: ViewHolder) {
        super.onViewRecycled(holder)
        glideRequest.clear((holder.binding as ItemGridThumbnailBinding).iv)
    }
}