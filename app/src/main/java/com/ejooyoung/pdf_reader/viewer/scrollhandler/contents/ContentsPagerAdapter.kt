package com.ejooyoung.pdf_reader.viewer.scrollhandler.contents

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ejooyoung.pdf_reader.database.model.Book
import com.ejooyoung.pdf_reader.viewer.scrollhandler.contents.bookmark.BookmarkListFragment
import com.ejooyoung.pdf_reader.viewer.scrollhandler.contents.contents.ContentsListFragment
import com.ejooyoung.pdf_reader.viewer.scrollhandler.contents.model.ContentsType
import java.lang.Exception

class ContentsPagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    private val book: Book,
    private val contentsClickListener: ContentsClickListener
): FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount() = ContentsType.size()

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            ContentsType.CONTENT.type -> ContentsListFragment.newInstance(contentsClickListener)
            ContentsType.BOOKMARK.type -> BookmarkListFragment.newInstance(book, contentsClickListener)
            else -> throw Exception("unknown type")
        }
    }
}