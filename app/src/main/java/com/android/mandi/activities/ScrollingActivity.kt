package com.android.mandi.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.core.view.ViewCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.mandi.PropertyInterface
import com.android.mandi.R
import com.android.mandi.adapters.PropertyAdapter
import com.android.mandi.dto.PropertyMatchDto
import com.android.mandi.model.ScrollingModel
import com.android.mandi.viewModel.ScrollingViewModel
import com.android.mandi.viewModel.ScrollingViewModelImpl
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_scrolling.*
import kotlinx.android.synthetic.main.content_scrolling.*
import javax.inject.Inject


class ScrollingActivity : DaggerAppCompatActivity(), SwipeRefreshLayout.OnRefreshListener,
    PropertyInterface {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: ScrollingViewModel
    private lateinit var adapter: PropertyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scrolling)
        getAssociatedViewModel()

        swipeToRefreshView.setOnRefreshListener(this)
        viewModel.showMessage().observe(this, messageObserver)
        viewModel.showHideLoader().observe(this, loaderObserver)
        viewModel.showPoetryLiveData().observe(this, propertyLiveDataObserver)
        viewModel.showPropertyOptionsData().observe(this, propertyOptionsObserver)
        viewModel.getFacilityList()
        viewModel.getOptionsList()
        viewModel.getExclusionsList()

    }

    private fun getAssociatedViewModel() {
        viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(ScrollingViewModelImpl::class.java)
        viewModel.bindModel(ScrollingModel())
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setUpRecyclerView() {
        rvSabjiList.setHasFixedSize(true)
        rvSabjiList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvSabjiList.adapter = adapter
        ViewCompat.setNestedScrollingEnabled(rvSabjiList, false)
    }

    private val propertyLiveDataObserver = Observer<PropertyMatchDto.Response> { response ->
        viewModel.getOptionsList()
    }

    private val propertyOptionsObserver =
        Observer<List<PropertyMatchDto.OptionsObj>> {
            generateList()
        }

    override fun generateList() {
        adapter = PropertyAdapter(viewModel, this)
        setUpRecyclerView()
        adapter.clear()
        val facilityList = viewModel.getBoundModel()?.facilityList
        val propertyList = viewModel.getBoundModel()?.optionsList
        if (facilityList != null && facilityList.isNotEmpty() && propertyList != null && propertyList.isNotEmpty()) {
            for ((index, facility) in facilityList.withIndex()) {
                facility.options = ArrayList()
                for (optionObj in propertyList) {
                    if (facility.facilityId == optionObj.facilityId) {
                        facility.options.add(optionObj)
                    }
                }
                val taskItem = PropertyAdapter.FacilityRecord(index.toLong(), facility)
                adapter.addRecyclerViewItem(taskItem)
            }
        } else {
            viewModel.getPropertyLiveData()
        }
    }

    @SuppressLint("ShowToast")
    private val messageObserver = Observer<String> { message ->
        tvError.visibility = View.VISIBLE
        tvError.text = message
    }

    private val loaderObserver = Observer<Boolean> { value ->
        swipeToRefreshView.isRefreshing = value
    }

    override fun onRefresh() {
        viewModel.getPropertyLiveData()
        viewModel.getFacilityList()
        viewModel.getOptionsList()
        viewModel.getExclusionsList()
    }

}