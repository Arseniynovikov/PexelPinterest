package com.example.pinterest.ui.details

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pinterest.data.models.BookmarkImages
import com.example.pinterest.data.models.UIImage
import com.example.pinterest.repository.PhotoRepository
import com.example.pinterest.data.pexelModels.Photo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repository: PhotoRepository
) : ViewModel() {

    private val _isBookmarked = MutableLiveData<Boolean>()
    val isBookmarked: LiveData<Boolean> = _isBookmarked

    private val _image = MutableLiveData<UIImage>()
    val photo: LiveData<UIImage> = _image

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun loadImageDetails(id: Long) {
        viewModelScope.launch {
            _loading.value = true
            try {
                _image.value = repository.getImageById(id)
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _loading.value = false
            }
        }
    }

    fun addNewBookmark(){
        viewModelScope.launch {
            _image.value?.let { repository.insertBookmark(it) }
            _isBookmarked.value = true
        }
    }

    fun deleteBookmark(id: Long){
        viewModelScope.launch {
            repository.deleteBookmark(id)
            _isBookmarked.value = false
        }
    }

    fun checkBookmark(id: Long) {

        viewModelScope.launch {
            _isBookmarked.value = repository.isBookmarked(id)
        }
        Log.e("isBooked", "${_isBookmarked.value}")
    }
}