package com.ejooyoung.pdf_reader.viewer.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ejooyoung.pdf_reader.R
import com.ejooyoung.pdf_reader.ViewModelFactories
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
    val viewModel by viewModels<ContentsViewModel> {
        ViewModelFactories.of(requireActivity().application, this, book)
    }

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
        binding.viewPager2.adapter = ContentsPagerAdapter(childFragmentManager, lifecycle)
        val tabNameArray = resources.getStringArray(R.array.CONTENTS_TAB_NAME)
        TabLayoutMediator(binding.layTab, binding.viewPager2) { tab: TabLayout.Tab, position: Int ->
            tab.text = tabNameArray[position]
        }.attach()
    }

    class ContentsPagerAdapter(
        fragmentManager: FragmentManager,
        lifecycle: Lifecycle
    ): FragmentStateAdapter(fragmentManager, lifecycle) {

        companion object {
            const val TAB_SIZE = 2
        }

        override fun getItemCount() = TAB_SIZE

        override fun createFragment(position: Int): Fragment {
            return ContentsListFragment.newInstance(ContentsType.valueOf(position))
        }
    }
}