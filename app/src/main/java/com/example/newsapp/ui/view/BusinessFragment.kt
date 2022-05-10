package com.example.newsapp.ui.view

import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentBusinessBinding
import com.example.newsapp.db.ConcreteLocalSource
import com.example.newsapp.model.NewsReponse
import com.example.newsapp.model.Repository
import com.example.newsapp.network.NewsClient
import com.example.newsapp.ui.Adapters.NewsAdapter
import com.example.newsapp.ui.viewmodel.NewsViewModel
import com.example.newsapp.ui.viewmodel.NewsViewModelFactory
import com.example.newsapp.utils.Connection

class BusinessFragment : Fragment() {

    private var _binding: FragmentBusinessBinding? = null
    private val factory by lazy { NewsViewModelFactory(
        Repository.getInstance(requireContext(),
            ConcreteLocalSource(requireContext()), NewsClient.getInstance()),requireActivity().application) }
    private val viewModel by lazy {ViewModelProvider(requireActivity(),factory)[NewsViewModel::class.java]}
    private val defaultPref by lazy { PreferenceManager.getDefaultSharedPreferences(requireContext())}
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBusinessBinding.inflate(inflater, container, false)
        val root: View = binding.root
        setUpRecyclerView()
        observeNews()
        handleRefresher()
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setUpRecyclerView() = binding.apply {
        businessRecycler.layoutManager = LinearLayoutManager(requireContext())
        businessRecycler.adapter = NewsAdapter(requireContext())
    }

    private fun observeNews(){
        if(Connection.checkForInternet(requireContext())){
            viewModel.checkCountry()
            viewModel.getBusinessNews()
            viewModel.businessNews.observe(viewLifecycleOwner) {
                fillNewsData(it)
                binding.progressBar.visibility = ProgressBar.INVISIBLE
                binding.refresher.isRefreshing = false
            }
        }else{
            binding.apply {
                progressBar.visibility = ProgressBar.INVISIBLE
                refresher.isRefreshing = false
            }
            Toast.makeText(requireContext(), "Not Internet Connection", Toast.LENGTH_SHORT).show()
        }
    }

    private fun fillNewsData(apiResponse: NewsReponse) = binding.apply {
        (businessRecycler.adapter as NewsAdapter).setData(apiResponse.articles)
    }

    private fun handleRefresher() = binding.refresher.apply {
        setColorSchemeColors(resources.getColor(R.color.my_primary,null))
        setOnRefreshListener {
            isRefreshing = true
            observeNews()
        }
    }
}