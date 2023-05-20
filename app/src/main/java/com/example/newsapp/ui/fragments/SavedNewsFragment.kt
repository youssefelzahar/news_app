package com.example.newsapp.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.adapter.NewsAdapter
import com.example.newsapp.ui.MainActivity
import com.example.newsapp.viewmodels.NewsViewModel
import com.google.android.material.snackbar.Snackbar

class SavedNewsFragment:Fragment(R.layout.fragment_saved_news) {
    lateinit var viewModel: NewsViewModel
    lateinit var newsAdapter: NewsAdapter
    lateinit var recyclerView: RecyclerView
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel as NewsViewModel
        setupRecyclerView()

        newsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply { putSerializable("article", it) }
            findNavController().navigate(
                R.id.action_savedNewsFragment_to_articleNewsFragment,
                bundle
            )
        }
        val iteamtouchhelper= object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position=viewHolder.adapterPosition
                val article=newsAdapter.differ.currentList[position]
                viewModel.deletenews(article)
                Snackbar.make(view,"delete successfull",Snackbar.LENGTH_SHORT).apply {
                    setAction("Undo"){
                        viewModel.savarticle(article)
                    }
                    show()
                }
            }

        }


        viewModel.getSavedNews().observe(viewLifecycleOwner, Observer {
            articles->newsAdapter.differ.submitList(articles)
        })


    }

    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter()
        recyclerView.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}