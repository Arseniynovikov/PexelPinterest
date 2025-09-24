package com.example.pinterest.ui.bookmarks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pinterest.data.models.BookmarkImages
import com.example.pinterest.data.pexelModels.Photo
import com.example.pinterest.repository.PhotoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel  @Inject constructor(
    private val repository: PhotoRepository
) : ViewModel() {
    private val _bookmarks = MutableLiveData<List<BookmarkImages>>()
    val bookmarks: LiveData<List<BookmarkImages>> = _bookmarks

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    fun getBookmarkImages(){
        viewModelScope.launch{
            val bookmarkList = repository.getBookmarks()
            _bookmarks.value = bookmarkList
        }
    }
}