package com.example.submissionjetpackcompose.ui.view_model

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.submissionjetpackcompose.data.UiState
import com.example.submissionjetpackcompose.data.entity.AnimeEntity
import com.example.submissionjetpackcompose.data.repository.AnimeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class AnimeListViewModel(
    private val repository: AnimeRepository
): ViewModel() {
    private val _uiState = MutableStateFlow<UiState<List<AnimeEntity>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<AnimeEntity>>> = _uiState.asStateFlow()

    fun getAllAnime() {
        viewModelScope.launch {
            repository
                .getAnimeList()
                .catch {
                    _uiState.value = UiState.Error("Error")
                }
                .collect {
                    if(it.isNotEmpty()) {
                        _uiState.value = UiState.Success(it)
                    }
                    else{
                        _uiState.value = UiState.Error("Error")
                    }
                }
        }
    }

    private val _emptyQuery = mutableStateOf(false)
    val emptyQuery: State<Boolean> get() = _emptyQuery

    private val _query = mutableStateOf("")
    val query: State<String> get() = _query
    fun search(newQuery: String) {
        _emptyQuery.value = newQuery == ""
        _query.value = newQuery
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            repository
                .searchAnime(_query.value)
                .collect { item ->
                    if(item.isNotEmpty()) {
                        _uiState.value = UiState.Success(item)
                    }
                    else{
                        _uiState.value = UiState.Error("Error")
                    }
                }
        }
    }
}