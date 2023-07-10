package com.example.submissionjetpackcompose.ui.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.submissionjetpackcompose.data.UiState
import com.example.submissionjetpackcompose.data.entity.AnimeEntity
import com.example.submissionjetpackcompose.data.repository.AnimeRepository
import com.example.submissionjetpackcompose.model.DetailAnime
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class AnimeDetailViewModel(
    private val repository: AnimeRepository
): ViewModel() {
    private val _uiState = MutableStateFlow<UiState<DetailAnime?>>(UiState.Loading)
    val uiState: StateFlow<UiState<DetailAnime?>> = _uiState.asStateFlow()

    fun getDetailAnime(id: Int) {
        viewModelScope.launch {
            repository
                .getAnimeDetail(id)
                .catch {
                    _uiState.value = UiState.Error("Error")
                }
                .collect {
                    if(it!=null) {
                        _uiState.value = UiState.Success(it)
                    }
                    else{
                        _uiState.value = UiState.Error("Error")
                    }
                }
        }
    }
}