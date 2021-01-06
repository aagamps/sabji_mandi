package com.android.mandi.activities

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import com.google.android.material.appbar.CollapsingToolbarLayout
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.android.mandi.R
import com.android.mandi.model.ScrollingModel
import com.android.mandi.viewModel.ScrollingViewModel
import com.android.mandi.viewModel.ScrollingViewModelImpl
import javax.inject.Inject


class ScrollingActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: ScrollingViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scrolling)
        getAssociatedViewModel()
        setSupportActionBar(findViewById(R.id.toolbar))
        findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout).title = title
        viewModel.getSabjiMandiList()
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
}