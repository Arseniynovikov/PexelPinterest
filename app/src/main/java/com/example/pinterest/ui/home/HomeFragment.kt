package com.example.pinterest.ui.home


import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.pinterest.databinding.FragmentHomeBinding
import com.example.pinterest.utils.isNetworkAvailable
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()

    private lateinit var adapter: HomeItemPhotoAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        networkState(requireContext())


        adapter = HomeItemPhotoAdapter(onClick = { image ->
            val action = HomeFragmentDirections.actionNavigationHomeToNavigationDetail(image.id)
            findNavController().navigate(action)
        })

        binding.recyclerView.apply {

            val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            layoutManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS

            binding.recyclerView.layoutManager = layoutManager
            binding.recyclerView.setHasFixedSize(true)
            adapter = this@HomeFragment.adapter
        }


        viewModel.image.observe(viewLifecycleOwner) { list ->
            adapter.submitList(list)
            noDataFound(list.isEmpty())
        }
        viewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
        editMethod()

    }

    private fun networkState(context: Context){
        if (!context.isNetworkAvailable()) {
            binding.recyclerView.visibility = View.GONE
            binding.progressBar.visibility = View.GONE
            binding.noInternetView.visibility = View.VISIBLE

            binding.reloadButton.setOnClickListener{
                networkState(requireContext())
                editMethod()
            }

            Toast.makeText(context, "No internet connection.", Toast.LENGTH_SHORT).show()

        } else {
            binding.recyclerView.visibility = View.VISIBLE
            binding.progressBar.visibility = View.VISIBLE
            binding.noInternetView.visibility = View.GONE
        }

    }

    private fun editMethod(){
        viewModel.loadCurated()
        binding.searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s.toString().trim()
                if (query.isNotEmpty()) {
                    viewModel.search(query)

                    binding.clearTextButton.visibility = View.VISIBLE
                } else {
                    viewModel.loadCurated()
                    binding.clearTextButton.visibility = View.GONE
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
        binding.clearTextButton.setOnClickListener {
            binding.searchEditText.setText("")
            viewModel.loadCurated()
            binding.clearTextButton.visibility = View.GONE
        }
    }

    private fun noDataFound(dataRequest: Boolean){
        if (dataRequest) {
            binding.recyclerView.visibility = View.GONE
            binding.progressBar.visibility = View.GONE
            binding.noDataFounded.visibility = View.VISIBLE

            binding.exploreButton.setOnClickListener{
                binding.searchEditText.setText("")
                editMethod()
            }

        } else {
            binding.recyclerView.visibility = View.VISIBLE
            binding.progressBar.visibility = View.VISIBLE
            binding.noDataFounded.visibility = View.GONE
        }

    }

}