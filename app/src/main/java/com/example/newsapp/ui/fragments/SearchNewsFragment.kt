package com.example.newsapp.ui.fragments

import android.content.ContentValues.TAG
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.adapter.NewsAdapter
import com.example.newsapp.ui.MainActivity
import com.example.newsapp.utilite.Constances.Companion.Search_News_Time_Delay
import com.example.newsapp.utilite.ResonseState
import com.example.newsapp.viewmodels.NewsViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchNewsFragment:Fragment(R.layout.fragment_search_news) {
    lateinit var viewModel: NewsViewModel
    lateinit var newsAdapter: NewsAdapter
    val TAG = "SearchNewsFragment"
    lateinit var progressBar: ProgressBar
    lateinit var recyclerView: RecyclerView
    lateinit var editText: EditText
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel= (activity as MainActivity).viewModel as NewsViewModel
        setupRecyclerView()
        progressBar=view.findViewById(R.id.paginationProgressBar)
        recyclerView=view.findViewById(R.id.rvBreakingNews)
        editText=view.findViewById(R.id.etSearch)
        var job:Job?=null
        newsAdapter.setOnItemClickListener { val bundle=Bundle().apply { putSerializable("article",it) }
            findNavController().navigate(R.id.action_searchNewsFragment_to_articleNewsFragment,bundle)}
        editText.addTextChangedListener {text: Editable? -> job?.cancel()
        job= MainScope().launch {
            delay(Search_News_Time_Delay)
            text?.let { if(text.toString().isEmpty()){
                viewModel.getsearchnews(text.toString())
            } }
        }
        }

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