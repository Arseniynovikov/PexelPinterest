package com.example.pinterest.ui.bookmarks

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.pinterest.databinding.FragmentBookmarkBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookmarkFragment : Fragment() {

    private lateinit var binding: FragmentBookmarkBinding
    private val viewModel: BookmarkViewModel by viewModels()

    private lateinit var adapter: BookmarkItemAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBookmarkBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = BookmarkItemAdapter(onClick = { image ->
            val action = BookmarkFragmentDirections.actionNavigationBookmarkToNavigationDetail(image.id)
            findNavController().navigate(action)
        })

        binding.recyclerView.apply {

            val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            layoutManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS

            binding.recyclerView.layoutManager = layoutManager
            binding.recyclerView.setHasFixedSize(true)
            adapter = this@BookmarkFragment.adapter
        }

        viewModel.getBookmarkImages()

        viewModel.bookmarks.observe(viewLifecycleOwner) { list ->
            adapter.submitList(list)
            bookmarkListIsEmpty(list.isEmpty())
        }
    }

    private fun bookmarkListIsEmpty(empty: Boolean){
        if(empty){
            binding.bookmarkIsFull.visibility = View.GONE
            binding.bookmarkIsEmpty.visibility = View.VISIBLE
            binding.exploreButton.setOnClickListener{
                val action = BookmarkFragmentDirections.actionNavigationBookmarkToNavigationHome()
                findNavController().navigate(action)
            }
        } else {
            binding.bookmarkIsFull.visibility = View.VISIBLE
            binding.bookmarkIsEmpty.visibility = View.GONE
        }
    }
}