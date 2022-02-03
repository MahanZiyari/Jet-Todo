package com.mahan.compose.jettodo.ui.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahan.compose.jettodo.data.TodoRepository
import com.mahan.compose.jettodo.data.models.Priority
import com.mahan.compose.jettodo.data.models.TodoTask
import com.mahan.compose.jettodo.util.Constants
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

    // Selected Task
    private val _selectedTask: MutableStateFlow<TodoTask?> = MutableStateFlow(null)
    val selectedTask = _selectedTask.asStateFlow()

    // TaskScreen States
    val id = mutableStateOf(0)
    val title = mutableStateOf("")
    val description = mutableStateOf("")
    val priority = mutableStateOf(Priority.Low)

    fun updateTitle(newTitle: String) {
        if (newTitle.length < Constants.MAX_TITLE_CHARACTER_COUNT)
            title.value = newTitle
    }

    fun updateDescription(newValue: String) {
        description.value = newValue
    }

    fun updatePriority(newValue: Priority) {
        priority.value = newValue
    }


    fun getAllTasks() {
        _tasks.value = RequestState.Loading
        viewModelScope.launch {
            repository.getAllTasks().collect {
                _tasks.value = RequestState.Success(data = it)
            }
        }
    }

    fun getSelectedTask(taskId: Int) {
        viewModelScope.launch {
            repository.getSelectedTask(taskId = taskId).collect {
                _selectedTask.value = it
            }
        }
    }

    fun updateTaskContentFields(selectedTask: TodoTask?) {
        if (selectedTask != null) {
            id.value = selectedTask.id
            title.value = selectedTask.title
            description.value = selectedTask.description
            priority.value = selectedTask.priority
        } else {
            id.value = 0
            title.value = ""
            description.value = ""
            priority.value = Priority.Low
        }
    }
}