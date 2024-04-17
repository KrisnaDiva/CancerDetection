package com.dicoding.asclepius.view

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.asclepius.adapter.NewsAdapter
import com.dicoding.asclepius.data.remote.response.ArticlesItem
import com.dicoding.asclepius.databinding.FragmentNewsBinding
import com.dicoding.asclepius.utils.ViewUtils
import com.dicoding.asclepius.viewmodel.NewsViewModel

class NewsFragment : Fragment() {
    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!
    private val newsViewModel: NewsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsBinding.inflate(inflater, container, false)
        setupRecyclerView()
        observeViewModel()
        return binding.root
    }

    private fun setupRecyclerView() {
        val orientation = resources.configuration.orientation
        val layoutManager = if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            GridLayoutManager(context, 2)
        } else {
            LinearLayoutManager(context)
        }

        with(binding.rvNews) {
            this.layoutManager = layoutManager
        }
    }

    private fun observeViewModel() {
        newsViewModel.listNews.observe(viewLifecycleOwner) {
            setNewsData(it)
        }
        ViewUtils.observeToastMessage(requireContext(), newsViewModel.toastMessage)
        ViewUtils.observeLoading(binding.progressBar, newsViewModel.isLoading)
    }

    private fun setNewsData(newsData: List<ArticlesItem>) {
        val adapter = NewsAdapter()
        adapter.submitList(newsData)
        binding.rvNews.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}