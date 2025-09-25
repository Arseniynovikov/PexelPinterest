package com.example.pinterest.ui.details

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
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
import androidx.core.net.toUri

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


        viewModel.loadImageDetails(photoId)

        viewModel.photo.observe(viewLifecycleOwner) { photo ->
            binding.nameText.text = photo.photographer
            Glide.with(this)
                .load(photo.largeSrc)
                .into(binding.imageView)
        }


        viewModel.checkBookmark(photoId)
        viewModel.isBookmarked.observe(viewLifecycleOwner) { bookmarked ->

            if (bookmarked) {
                binding.bookmarkButton.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.circle_red)
                binding.bookmarkButton.setOnClickListener {
                    viewModel.deleteBookmark(photoId)
                }
            } else {
                binding.bookmarkButton.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.circle_gray)
                binding.bookmarkButton.setOnClickListener {
                    viewModel.addNewBookmark()
                }
            }
        }

        binding.downloadButton.setOnClickListener {
            downloadImage(viewModel.photo.value!!.largeSrc, "${viewModel.photo.value!!.id}.jpg")
            Toast.makeText(requireContext(), "Downloading image...", Toast.LENGTH_SHORT).show()
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

    private fun downloadImage(url: String, fileName: String) {
        val request = DownloadManager.Request(url.toUri())
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName)
            .setAllowedOverMetered(true)
            .setAllowedOverRoaming(true)

        val downloadManager =
            requireContext().getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        downloadManager.enqueue(request)
    }
}