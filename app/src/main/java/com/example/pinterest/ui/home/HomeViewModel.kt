package com.example.pinterest.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pinterest.data.models.UIImage
import com.example.pinterest.repository.PhotoRepository
import com.example.pinterest.data.pexelModels.Photo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: PhotoRepository
) : ViewModel() {

    private val _image = MutableLiveData<List<UIImage>>()
    val image: LiveData<List<UIImage>> = _image

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    fun loadCurated(page: Int = 1, perPage: Int = 10) {
        viewModelScope.launch {
            _loading.value = true
            try {
                _image.value = repository.getCurated(page = page, perPage = perPage)

            } catch (e: Exception) {
                Log.e("HomeViewModel", "Error: ${e.message}")
            } finally {
                _loading.value = false
            }
        }
    }

    fun search(query: String) {
        viewModelScope.launch {
            _loading.value = true
            try {
                _image.value = repository.search(query, 1, 10)
            } catch (e: Exception) {

                Log.e("HomeViewModel", "Error: ${e.message}")


            } finally {
                _loading.value = false
            }
        }
    }
}