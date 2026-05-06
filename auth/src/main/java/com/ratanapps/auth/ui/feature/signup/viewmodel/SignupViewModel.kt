package com.ratanapps.auth.ui.feature.signup.viewmodel

import androidx.lifecycle.ViewModel
import com.ratanapps.auth.domain.usecase.AuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(val authUseCase: AuthUseCase): ViewModel() {






}