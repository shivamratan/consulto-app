package com.ratanapps.consultoapp.ui

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


// Global Activity ViewModel
@HiltViewModel
class HomeViewModel: ViewModel() {

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    // Global state for changing the state of theme
    var isDark = mutableStateOf(false)

    init {
        viewModelScope.launch {
            delay(500)
            _isLoading.value = false
        }
    }


}