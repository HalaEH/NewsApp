package com.example.newsapp.ui.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentScienceBinding
import com.example.newsapp.db.ConcreteLocalSource
import com.example.newsapp.model.NewsReponse
import com.example.newsapp.model.Repository
import com.example.newsapp.network.NewsClient
import com.example.newsapp.ui.Adapters.NewsAdapter
import com.example.newsapp.ui.viewmodel.NewsViewModel
import com.example.newsapp.ui.viewmodel.NewsViewModelFactory
import com.example.newsapp.utils.Connection

class ScienceFragment : Fragment() {

    private val binding by lazy { FragmentScienceBinding.inflate(layoutInflater) }
    private val factory by lazy { NewsViewModelFactory(
        Repository.getInstance(requireContext(), ConcreteLocalSource(requireContext()), NewsClient.getInstance()),
        requireActivity().application) }
    private val viewModel by lazy { ViewModelProvider(requireActivity(), factory)[NewsViewModel::class.java] }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setUpRecyclerView()
        observeNews()
        handleRefresher()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        observeNews()
    }

    private fun setUpRecyclerView() = binding.apply {
        scienceRecycler.layoutManager = LinearLayoutManager(requireContext())
        scienceRecycler.adapter = NewsAdapter(requireContext())
    }

    private fun observeNews(){
        if(Connection.checkForInternet(requireContext())){
            viewModel.checkCountry()
            viewModel.getSciNews()
            viewModel.sciNews.observe(viewLifecycleOwner) {
                fillNewsData(it)
                binding.progressBar.visibility = ProgressBar.INVISIBLE
                binding.refresher.isRefreshing = false

            }

        }
        else{
            binding.progressBar.visibility = ProgressBar.INVISIBLE
            binding.refresher.isRefreshing = false
            Toast.makeText(requireContext(), "Not Internet Connection", Toast.LENGTH_SHORT).show()
        }

    }

    private fun fillNewsData(apiResponse: NewsReponse) = binding.apply {
        (scienceRecycler.adapter as NewsAdapter).setData(apiResponse.articles)
    }

    private fun handleRefresher() = binding.refresher.apply {
        setColorSchemeColors(resources.getColor(R.color.my_primary,null))
        setOnRefreshListener {
            isRefreshing = true
            observeNews()
        }
    }


}