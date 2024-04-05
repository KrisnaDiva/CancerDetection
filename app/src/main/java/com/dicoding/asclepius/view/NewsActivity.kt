package com.dicoding.asclepius.view

import android.content.res.Configuration
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.asclepius.R
import com.dicoding.asclepius.adapter.NewsAdapter
import com.dicoding.asclepius.data.remote.response.ArticlesItem
import com.dicoding.asclepius.databinding.ActivityNewsBinding
import com.dicoding.asclepius.utils.ActionBarUtils
import com.dicoding.asclepius.utils.ViewUtils
import com.dicoding.asclepius.viewmodel.NewsViewModel

class NewsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewsBinding
    private val newsViewModel: NewsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBinding()
        ActionBarUtils.setupActionBar(this, getString(R.string.article))
        setupRecyclerView()
        observeViewModel()
    }

    private fun setupBinding() {
        binding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun setupRecyclerView() {
        val orientation = resources.configuration.orientation
        val layoutManager = if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            GridLayoutManager(this, 2)
        } else {
            LinearLayoutManager(this)
        }

        with(binding.rvNews) {
            this.layoutManager = layoutManager
            addItemDecoration(
                DividerItemDecoration(
                    context,
                    layoutManager.orientation
                )
            )
        }
    }

    private fun observeViewModel() {
        newsViewModel.listNews.observe(this) {
            setNewsData(it)
        }
        ViewUtils.observeToastMessage(this, newsViewModel.toastMessage)
        ViewUtils.observeLoading(binding.progressBar, newsViewModel.isLoading)
    }

    private fun setNewsData(newsData: List<ArticlesItem>) {
        val adapter = NewsAdapter()
        adapter.submitList(newsData)
        binding.rvNews.adapter = adapter
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return ActionBarUtils.onOptionsItemSelected(this, item)
    }
}