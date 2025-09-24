package com.example.pinterest.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pinterest.databinding.FragmentHomeBinding
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


        adapter = HomeItemPhotoAdapter(onClick = { photo ->
            val action = HomeFragmentDirections.actionNavigationHomeToNavigationDetail(photo.id)
            findNavController().navigate(action)
        })
        binding.recyclerView.apply {
            binding.recyclerView.setHasFixedSize(true)
            binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = this@HomeFragment.adapter
        }


        observeUi()

        viewModel.loadCurated()
    }

    private fun observeUi() {
        viewModel.photos.observe(viewLifecycleOwner) { list ->
            adapter.submitList(list)
        }
        viewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

    }
}