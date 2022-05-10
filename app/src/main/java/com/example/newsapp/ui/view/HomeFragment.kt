package com.example.newsapp.ui.view

import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentHomeBinding
import com.example.newsapp.db.ConcreteLocalSource
import com.example.newsapp.model.NewsReponse
import com.example.newsapp.model.Repository
import com.example.newsapp.network.NewsClient
import com.example.newsapp.ui.Adapters.NewsAdapter
import com.example.newsapp.ui.viewmodel.NewsViewModel
import com.example.newsapp.ui.viewmodel.NewsViewModelFactory
import com.example.newsapp.utils.Connection
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val factory by lazy { NewsViewModelFactory(Repository.getInstance(requireContext(),ConcreteLocalSource(requireContext()), NewsClient.getInstance()),requireActivity().application) }
    private val viewModel by lazy {ViewModelProvider(requireActivity(),factory)[NewsViewModel::class.java]}
    private val defaultPref by lazy {PreferenceManager.getDefaultSharedPreferences(requireContext())}
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        setUpRecyclerView()
        observeNews()
        handleSearchView()
        handleRefresher()
        return root
    }

    override fun onResume() {
        super.onResume()
        observeNews()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setUpRecyclerView() = binding.apply {
        newsRecycler.layoutManager = LinearLayoutManager(requireContext())
        newsRecycler.adapter = NewsAdapter(requireContext())
    }

    private fun observeNews(){
        if(Connection.checkForInternet(requireContext())){
            binding.apply {
                noConnection.visibility = View.GONE
                binding.card.visibility = View.VISIBLE
                searchView.visibility = View.VISIBLE
            }
            viewModel.checkCountry()
            viewModel.getNews()
            viewModel.news.observe(viewLifecycleOwner) {
                fillNewsData(it)
                binding.progressBar.visibility = ProgressBar.INVISIBLE
                binding.refresher.isRefreshing = false
            }
        }else{
            binding.apply {
                noConnection.visibility = View.VISIBLE
                binding.card.visibility = View.INVISIBLE
                searchView.visibility = View.INVISIBLE
                progressBar.visibility = ProgressBar.INVISIBLE
                refresher.isRefreshing = false
            }
            Toast.makeText(requireContext(), "Not Internet Connection", Toast.LENGTH_SHORT).show()
        }
    }

    private fun fillNewsData(apiResponse: NewsReponse) = binding.apply {
        (newsRecycler.adapter as NewsAdapter).setData(apiResponse.articles)
    }

    private fun handleSearchView() = binding.searchView.apply {
        var job: Job?= null

        setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                job?.cancel()
                job = MainScope().launch {
                    delay(500L)
                    if(p0!!.isNotEmpty()){
                        viewModel.getSearchResult(p0,
                            defaultPref.getString("search", "publishedAt")!!
                        )
                    }
                    else{
                        viewModel.getNews()
                    }
                }
                return false
            }

        })
    }

    private fun handleRefresher() = binding.refresher.apply {
        setColorSchemeColors(resources.getColor(R.color.my_primary,null))
        setOnRefreshListener {
            isRefreshing = true
            observeNews()
        }
    }
}