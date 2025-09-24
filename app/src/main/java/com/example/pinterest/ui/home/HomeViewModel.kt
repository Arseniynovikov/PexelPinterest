package com.example.pinterest.ui.home

import android.util.Log
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
class HomeViewModel @Inject constructor(
    private val repository: PhotoRepository
) : ViewModel() {

    private val _photos = MutableLiveData<List<Photo>>()
    val photos: LiveData<List<Photo>> = _photos

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    fun loadCurated(page: Int = 1, perPage: Int = 20) {
        viewModelScope.launch {
            _loading.value = true
            try {
                val response = repository.getCurated(page = page, perPage = perPage)
                _photos.value = response.photos
                Log.e("home", "${photos.value}")
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Error: ${e.message}")
            } finally {
                _loading.value = false
            }
        }
    }
}