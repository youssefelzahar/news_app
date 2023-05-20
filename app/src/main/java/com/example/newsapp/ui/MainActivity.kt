package com.example.newsapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.manager.SupportRequestManagerFragment
import com.example.newsapp.R
import com.example.newsapp.db.ArticelDataBase
import com.example.newsapp.repo.NewsRersotry
import com.example.newsapp.viewmodels.NewsViewModel
import com.example.newsapp.viewmodels.NewsViewroviderFactory
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    lateinit var viewModel: ViewModel
    lateinit var bottomNav : BottomNavigationView
    lateinit var fragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomNav=findViewById(R.id.bottomNavigationView)
        fragment=supportFragmentManager.findFragmentById(R.id.newsNavHostFragment) as NavHostFragment
        val newreprosotry=NewsRersotry(ArticelDataBase(this))
        val viewmodelproviderfactory=NewsViewroviderFactory(newreprosotry)
        viewModel=ViewModelProvider(this,viewmodelproviderfactory).get(NewsViewModel::class.java)
        bottomNav.setupWithNavController(fragment.findNavController())
    }
}