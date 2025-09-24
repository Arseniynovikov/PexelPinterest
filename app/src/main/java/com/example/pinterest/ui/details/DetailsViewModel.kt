package com.example.pinterest.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pinterest.PhotoRepository
import com.example.pinterest.data.pexelModels.Photo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repository: PhotoRepository
) : ViewModel() {

    private val _photo = MutableLiveData<Photo>()
    val photo: LiveData<Photo> = _photo

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun loadPhotoDetails(id: Long) {
        viewModelScope.launch {
            _loading.value = true
            try {
                _photo.value = repository.getPhotoDetails(id)
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _loading.value = false
            }
        }
    }

}