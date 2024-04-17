package com.dicoding.asclepius.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.asclepius.R
import com.dicoding.asclepius.adapter.HistoryAdapter
import com.dicoding.asclepius.databinding.FragmentHistoryBinding
import com.dicoding.asclepius.factory.HistoryViewModelFactory
import com.dicoding.asclepius.viewmodel.HistoryViewModel

class HistoryFragment : Fragment() {
    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: HistoryAdapter
    private lateinit var historyViewModel: HistoryViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        setupViewModel()
        setupRecyclerView()
        observeHistory()
        binding.btnClear.setOnClickListener {
            historyViewModel.deleteAll()
        }
        return binding.root
    }

    private fun setupViewModel() {
        val factory = HistoryViewModelFactory.getInstance(requireActivity().application)
        historyViewModel = ViewModelProvider(this, factory)[HistoryViewModel::class.java]
    }

    private fun setupRecyclerView() {
        adapter = HistoryAdapter(historyViewModel)
        with(binding.rvHistory) {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(
                DividerItemDecoration(
                    context,
                    (layoutManager as LinearLayoutManager).orientation
                )
            )
            setHasFixedSize(true)
            adapter = this@HistoryFragment.adapter
        }
    }

    private fun observeHistory() {
        historyViewModel.getAll().observe(viewLifecycleOwner) { historyList ->
            historyList?.let { adapter.setListHistory(it) }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}