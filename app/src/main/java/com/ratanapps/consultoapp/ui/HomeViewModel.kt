package com.ratanapps.consultoapp.ui

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ratanapps.auth.domain.usecase.LogoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


// Global Activity ViewModel
@HiltViewModel
class HomeViewModel @Inject constructor(val logoutUseCase: LogoutUseCase): ViewModel() {

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    // Global state for changing the state of theme
    var isDark = mutableStateOf(false)

    init {
        viewModelScope.launch {
            delay(SPLASH_SCREEN_DELAY)
            _isLoading.value = false
        }
    }

    companion object {
        private const val SPLASH_SCREEN_DELAY = 500L
    }

    fun logout() {
        viewModelScope.launch {
            logoutUseCase.invoke()
        }
    }
}
