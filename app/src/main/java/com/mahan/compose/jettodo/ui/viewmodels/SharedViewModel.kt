package com.mahan.compose.jettodo.ui.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahan.compose.jettodo.data.TodoRepository
import com.mahan.compose.jettodo.data.models.TodoTask
import com.mahan.compose.jettodo.util.SearchAppBarState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(private val repository: TodoRepository) : ViewModel() {

    private val _tasks = MutableStateFlow<List<TodoTask>>(emptyList())
    val tasks = _tasks.asStateFlow().value

    val searchAppBarState: MutableState<SearchAppBarState> = mutableStateOf(SearchAppBarState.CLOSED)

    val searchAppBarText: MutableState<String> = mutableStateOf("")


    fun getAllTasks() {
        viewModelScope.launch {
            repository.getAllTasks().collect {
                _tasks.value = it
            }
        }
    }
}