package com.android.mandi.activities

import android.os.Bundle
import com.google.android.material.appbar.CollapsingToolbarLayout
import android.view.Menu
import android.view.MenuItem
import androidx.core.view.ViewCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.mandi.R
import com.android.mandi.adapters.SabjiAdapter
import com.android.mandi.dto.SabjiMandiDto
import com.android.mandi.model.ScrollingModel
import com.android.mandi.viewModel.ScrollingViewModel
import com.android.mandi.viewModel.ScrollingViewModelImpl
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_scrolling.*
import kotlinx.android.synthetic.main.content_scrolling.*
import javax.inject.Inject


class ScrollingActivity : DaggerAppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: ScrollingViewModel
    private lateinit var adapter: SabjiAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scrolling)
        getAssociatedViewModel()

        adapter = SabjiAdapter()
        setUpRecyclerView()
        swipeToRefreshView.setOnRefreshListener(this)
        swipeToRefreshView.post {
            onRefresh()
        }
        viewModel.showSabjiMadidata().observe(this, sabjiMandiDataObserver)
        viewModel.showMessage().observe(this, messageObserver)

    }

    private fun getAssociatedViewModel() {
        viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(ScrollingViewModelImpl::class.java)
        viewModel.bindModel(ScrollingModel())
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_scrolling, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

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
        viewModel.getLocationList()
        swipeToRefreshView.isRefreshing = false
    }

    private val messageObserver = Observer<String> { message ->
        swipeToRefreshView.isRefreshing = false
    }

    override fun onRefresh() {
        swipeToRefreshView.isRefreshing = true
        viewModel.getSabjiMandiNetworkData()
    }

}