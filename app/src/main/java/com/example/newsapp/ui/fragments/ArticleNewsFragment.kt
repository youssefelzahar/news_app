package com.example.newsapp.ui.fragments

import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.example.newsapp.R
import com.example.newsapp.ui.MainActivity
import com.example.newsapp.viewmodels.NewsViewModel
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.snackbar.Snackbar

class ArticleNewsFragment:Fragment(R.layout.fragment_article) {
    lateinit var viewModel: NewsViewModel
    lateinit var floatingActionButton: ExtendedFloatingActionButton
    val args:ArticleNewsFragmentArgs by navArgs()
    lateinit var webView: WebView
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        webView=view.findViewById(R.id.webView)
        floatingActionButton=view.findViewById(R.id.fab)
        viewModel= (activity as MainActivity).viewModel as NewsViewModel
        val article = args.article

        webView.apply {
            webViewClient = WebViewClient()
            loadUrl(article.url)
        }
        floatingActionButton.setOnClickListener{viewModel.savarticle(article)
        Snackbar.make(view,"Artcile saved successfully",Snackbar.LENGTH_SHORT).show()}


    }
}