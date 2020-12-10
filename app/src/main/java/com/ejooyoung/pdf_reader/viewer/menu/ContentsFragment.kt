package com.ejooyoung.pdf_reader.viewer.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ejooyoung.pdf_reader.R
import com.ejooyoung.pdf_reader.databinding.FragmentContentsBinding
import com.ejooyoung.pdf_reader.database.model.Book
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class ContentsFragment : Fragment() {

    companion object {
        fun newInstance(book: Book): ContentsFragment {
            return ContentsFragment().apply {
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
        binding.viewPager2.adapter = ContentsPagerAdapter(childFragmentManager, lifecycle, book)
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