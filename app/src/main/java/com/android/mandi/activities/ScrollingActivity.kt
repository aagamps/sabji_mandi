package com.android.mandi.activities

import android.annotation.SuppressLint
import android.os.Bundle
import com.google.android.material.appbar.CollapsingToolbarLayout
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.mandi.R
import com.android.mandi.adapters.LocationAdapter
import com.android.mandi.adapters.SabjiAdapter
import com.android.mandi.dto.SabjiMandiDto
import com.android.mandi.model.ScrollingModel
import com.android.mandi.viewModel.ScrollingViewModel
import com.android.mandi.viewModel.ScrollingViewModelImpl
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_scrolling.*
import kotlinx.android.synthetic.main.content_scrolling.*
import kotlinx.android.synthetic.main.layout_filter_bottomsheet.*
import java.time.Duration
import javax.inject.Inject


class ScrollingActivity : DaggerAppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: ScrollingViewModel
    private lateinit var adapter: SabjiAdapter
    private lateinit var adapterLocation: LocationAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scrolling)
        getAssociatedViewModel()

        adapter = SabjiAdapter()
        adapterLocation = LocationAdapter()

        setUpRecyclerView()
        swipeToRefreshView.setOnRefreshListener(this)
        swipeToRefreshView.post {
            onRefresh()
        }
        ivFliter.setOnClickListener {
            Toast.makeText(this, "Coming Soon...", Toast.LENGTH_SHORT)
        }
        viewModel.showMessage().observe(this, messageObserver)
        viewModel.showHideLoader().observe(this, loaderObserver)
        viewModel.showSabjiMadidata().observe(this, sabjiMandiDataObserver)
//        viewModel.showLocationList().observe(this, locationListObserver)                           // Created for Location List

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

//        rvLocation.setHasFixedSize(true)                                                          // Created for Location List
//        rvLocation.layoutManager =
//            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
//        rvLocation.adapter = adapterLocation
//        ViewCompat.setNestedScrollingEnabled(rvLocation, false)
    }

    private val sabjiMandiDataObserver = Observer<SabjiMandiDto.Response> { response ->

        val recordsList = response.records
        if (recordsList != null) {
            for ((index, meeting) in recordsList.withIndex()) {
                meeting.location = "${meeting.district}, ${meeting.state}"
                val taskItem = SabjiAdapter.SabjiRecord(index.toLong(), meeting)
                adapter.addRecyclerViewItem(taskItem)
            }
            adapter.notifyDataSetChanged()
        }
//        viewModel.getLocationList()                                                               // Created for Location List
        tvError.visibility = View.GONE
        swipeToRefreshView.isRefreshing = false
    }

    private val locationListObserver = Observer<List<String>> { locationList ->

        if (locationList != null) {
            for ((index, name) in locationList.withIndex()) {
                val location = SabjiMandiDto.Location(name = name, isSelected = false)
                val taskItem = LocationAdapter.LocationRecord(index.toLong(), location)
                adapterLocation.addRecyclerViewItem(taskItem)
            }
            adapterLocation.notifyDataSetChanged()
        }
//        viewModel.getLocationList()                                                               // Created for Location List
        swipeToRefreshView.isRefreshing = false
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
        viewModel.getSabjiMandiNetworkData()
//        viewModel.getLocationList()                                                               // Created for Location List
    }

}