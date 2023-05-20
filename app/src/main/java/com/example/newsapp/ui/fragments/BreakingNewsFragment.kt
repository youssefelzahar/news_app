package com.example.newsapp.ui.fragments

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.adapter.NewsAdapter
import com.example.newsapp.ui.MainActivity
import com.example.newsapp.utilite.ResonseState
import com.example.newsapp.viewmodels.NewsViewModel

class BreakingNewsFragment:Fragment(R.layout.fragment_breaking_news) {
    lateinit var viewModel: NewsViewModel
    lateinit var newsAdapter: NewsAdapter
    val TAG = "BreakingNewsFragment"
    lateinit var progressBar: ProgressBar
    lateinit var recyclerView: RecyclerView


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel= (activity as MainActivity).viewModel as NewsViewModel
        setupRecyclerView()
        progressBar=view.findViewById(R.id.paginationProgressBar)
        recyclerView=view.findViewById(R.id.rvBreakingNews)

        viewModel.news.observe(viewLifecycleOwner, Observer { response ->
            when(response) {
                is ResonseState.Success -> {
                    hideProgressBar()
                    response.data?.let { newsResponse ->
                        newsAdapter.differ.submitList(newsResponse.articles)
                    }
                }
                is ResonseState.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        Log.e(TAG, "An error occured: $message")
                    }
                }
                is ResonseState.Load -> {
                    showProgressBar()
                }
            }
        })
        newsAdapter.setOnItemClickListener { val bundle=Bundle().apply { putSerializable("article",it) }
        findNavController().navigate(R.id.action_breakingNewFragments_to_articleNewsFragment,bundle)}
    }



    private fun hideProgressBar() {
        progressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
    }

    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter()
        recyclerView.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    }
