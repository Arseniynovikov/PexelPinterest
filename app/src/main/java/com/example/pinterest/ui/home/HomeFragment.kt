package com.example.pinterest.ui.home

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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


        viewModel.photos.observe(viewLifecycleOwner) { list ->
            adapter.submitList(list)
        }
        viewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

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
        binding.clearTextButton.setOnClickListener{
            binding.searchEditText.setText("")
            binding.clearTextButton.visibility = View.GONE
        }
    }
}