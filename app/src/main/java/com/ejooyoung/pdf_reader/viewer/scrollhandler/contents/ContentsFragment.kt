package com.ejooyoung.pdf_reader.viewer.scrollhandler.contents

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ejooyoung.pdf_reader.R
import com.ejooyoung.pdf_reader.base.Const
import com.ejooyoung.pdf_reader.base.dialog.BookmarkPopupDialog
import com.ejooyoung.pdf_reader.databinding.FragmentContentsBinding
import com.ejooyoung.pdf_reader.database.model.Book
import com.ejooyoung.pdf_reader.database.model.Bookmark
import com.ejooyoung.pdf_reader.database.model.Contents
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class ContentsFragment : Fragment() {

    companion object {
        fun newInstance(book: Book): ContentsFragment {
            return ContentsFragment()
                .apply {
                this.book = book
            }
        }
    }

    private lateinit var book: Book
    private lateinit var binding: FragmentContentsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_contents, container, false)
        setupDataBinding(view)
        setupViewPager()
        return view
    }

    private fun setupDataBinding(view: View) {
        binding = FragmentContentsBinding.bind(view).apply {

        }
    }

    private fun setupViewPager() {
        binding.viewPager2.adapter =
            ContentsPagerAdapter(
                childFragmentManager,
                lifecycle,
                book,
                object :
                    ContentsClickListener {
                    override fun onClickContents(pageIdx: Int) {
                        val intent = Intent().apply {
                            putExtra(Const.KEY_BUNDLE_PAGE_INDEX, pageIdx)
                        }
                        with(requireActivity()) {
                            setResult(Activity.RESULT_OK, intent)
                            finish()
                        }
                    }

                    override fun onLongClickContents(contents: Contents): Boolean {
                        if (contents is Bookmark) {
                            BookmarkPopupDialog.newInstance(requireContext(), contents)
                                .show(childFragmentManager, null)
                        }
                        return true
                    }
                }
            )
        val tabNameArray = resources.getStringArray(R.array.CONTENTS_TAB_NAME)
        TabLayoutMediator(binding.layTab, binding.viewPager2) { tab: TabLayout.Tab, position: Int ->
            tab.text = tabNameArray[position]
        }.attach()

        binding.layTab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {}
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.let {
                    binding.tvName.text = it.text
                }
            }
        })
    }
}