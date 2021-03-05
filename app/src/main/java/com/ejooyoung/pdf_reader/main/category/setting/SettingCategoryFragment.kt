package com.ejooyoung.pdf_reader.main.category.setting

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import com.ejooyoung.pdf_reader.ViewModelFactories
import com.ejooyoung.pdf_reader.base.Const
import com.ejooyoung.pdf_reader.base.mvvm.BaseFragment
import com.ejooyoung.pdf_reader.base.widget.ViewHolder
import com.ejooyoung.pdf_reader.databinding.FragmentSettingCategoryBinding
import com.ejooyoung.pdf_reader.main.category.setting.listener.ItemMoveCallback
import com.ejooyoung.pdf_reader.main.category.setting.listener.ItemStartDragListener

class SettingCategoryFragment :
    BaseFragment<SettingCategoryViewModel, FragmentSettingCategoryBinding>(),
    ItemStartDragListener {

    private lateinit var itemTouchHelper: ItemTouchHelper

    companion object {
        fun newInstance(): SettingCategoryFragment {
            return SettingCategoryFragment()
        }
    }

    override fun setupViewModel() {
        viewModel = ViewModelFactories.of(requireActivity().application, this)
            .create(SettingCategoryViewModel::class.java)
    }

    override fun setupDataBinding(inflater: LayoutInflater, container: ViewGroup?) {
        binding = FragmentSettingCategoryBinding.inflate(inflater, container, false).apply {
            viewModel = this@SettingCategoryFragment.viewModel
        }
    }

    override fun setupObserver() {
        viewModel.itemList.observe(this, Observer {
            (binding.rvCategory.adapter as SettingCategoryAdapter).setItem(it)
        })
    }

    override fun onBindingCreated() {
        binding.rvCategory.adapter =
            SettingCategoryAdapter(viewModel.itemTouchListener, this).apply {
                setHasStableIds(true)
            }
        itemTouchHelper =
            ItemTouchHelper(ItemMoveCallback(binding.rvCategory.adapter as SettingCategoryAdapter)).apply {
                attachToRecyclerView(binding.rvCategory)
            }
    }

    fun onBackPressed(): Boolean {
        return viewModel.onBackPressed()
    }

    override fun onStartDrag(viewHolder: ViewHolder, event: MotionEvent) {
        if (event.action == MotionEvent.ACTION_DOWN) {
            itemTouchHelper.startDrag(viewHolder)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Const.KEY_REQUEST_ADD_CATEGORY_TO_BOOK && resultCode == Activity.RESULT_OK) {
            viewModel.onFinishAddCategoryToBook()
        }
    }
}