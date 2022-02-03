package com.mahan.compose.jettodo.ui.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahan.compose.jettodo.data.TodoRepository
import com.mahan.compose.jettodo.data.models.TodoTask
import com.mahan.compose.jettodo.util.RequestState
import com.mahan.compose.jettodo.util.SearchAppBarState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(private val repository: TodoRepository) : ViewModel() {

    private val _tasks = MutableStateFlow<RequestState<List<TodoTask>>>(RequestState.Idle)
    val tasks: StateFlow<RequestState<List<TodoTask>>> = _tasks.asStateFlow()

    val searchAppBarState: MutableState<SearchAppBarState> = mutableStateOf(SearchAppBarState.CLOSED)

    val searchAppBarText: MutableState<String> = mutableStateOf("")


    fun getAllTasks() {
        _tasks.value = RequestState.Loading
        viewModelScope.launch {
            repository.getAllTasks().collect {
                _tasks.value = RequestState.Success(data = it)
            }
        }
    }
}