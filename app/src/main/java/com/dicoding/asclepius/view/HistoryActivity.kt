package com.dicoding.asclepius.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.asclepius.R
import com.dicoding.asclepius.adapter.HistoryAdapter
import com.dicoding.asclepius.databinding.ActivityHistoryBinding
import com.dicoding.asclepius.factory.HistoryViewModelFactory
import com.dicoding.asclepius.utils.ActionBarUtils
import com.dicoding.asclepius.viewmodel.HistoryViewModel

class HistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHistoryBinding
    private lateinit var adapter: HistoryAdapter
    private lateinit var historyViewModel: HistoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBinding()
        ActionBarUtils.setupActionBar(this, getString(R.string.history))
        setupViewModel()
        setupRecyclerView()
        observeHistory()
    }

    private fun setupBinding() {
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun setupViewModel() {
        val factory = HistoryViewModelFactory.getInstance(application)
        historyViewModel = ViewModelProvider(this, factory)[HistoryViewModel::class.java]
    }

    private fun setupRecyclerView() {
        adapter = HistoryAdapter(historyViewModel)
        with(binding.rvHistory) {
            layoutManager = LinearLayoutManager(this@HistoryActivity)
            addItemDecoration(
                DividerItemDecoration(
                    this@HistoryActivity,
                    (layoutManager as LinearLayoutManager).orientation
                )
            )
            setHasFixedSize(true)
            adapter = this@HistoryActivity.adapter
        }
    }

    private fun observeHistory() {
        historyViewModel.getAll().observe(this) { historyList ->
            historyList?.let { adapter.setListHistory(it) }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.history_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_clear_all -> {
                historyViewModel.deleteAll()
                true
            }

            else -> ActionBarUtils.onOptionsItemSelected(this, item)
        }
    }
}