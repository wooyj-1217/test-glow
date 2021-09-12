package com.example.test_glow.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class DetailViewModel@Inject constructor(
    application: Application,
    private val savedStateHandle: SavedStateHandle
): AndroidViewModel(application) {

    val imageUri = savedStateHandle.getLiveData<String>("imageUri")
    val productTitle = savedStateHandle.getLiveData<String>("productTitle")

}