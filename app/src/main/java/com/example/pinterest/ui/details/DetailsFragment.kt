package com.example.pinterest.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.pinterest.MainActivity
import com.example.pinterest.R
import com.example.pinterest.databinding.FragmentDetailsBinding
import com.example.pinterest.ui.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private lateinit var binding: FragmentDetailsBinding

    private val viewModel: DetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }

        val photoId = arguments?.getLong("id")
            ?: throw IllegalArgumentException("Photo id required")

        viewModel.loadPhotoDetails(photoId)
        viewModel.photo.observe(viewLifecycleOwner) { photo ->
            binding.nameText.text = photo.photographer
            Glide.with(this)
                .load(photo.src.large)
                .into(binding.imageView)
        }

        viewModel.checkBookmark(photoId)
        viewModel.isBookmarked.observe(viewLifecycleOwner) { bookmarked ->

            if (bookmarked) {
                binding.bookmarkButton.setBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.red
                    )
                )
                binding.bookmarkButton.setOnClickListener {
                    viewModel.deleteBookmark(photoId)
                }
            } else {
                binding.bookmarkButton.setBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.dark_gray
                    )
                )
                binding.bookmarkButton.setOnClickListener {
                    viewModel.addNewBookmark()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        (requireActivity() as MainActivity).setBottomNavVisibility(false)
    }

    override fun onStop() {
        super.onStop()
        (requireActivity() as MainActivity).setBottomNavVisibility(true)
    }
}