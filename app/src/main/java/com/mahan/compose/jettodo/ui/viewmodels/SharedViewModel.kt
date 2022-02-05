package com.mahan.compose.jettodo.ui.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahan.compose.jettodo.data.DataStoreRepository
import com.mahan.compose.jettodo.data.TodoRepository
import com.mahan.compose.jettodo.data.models.Priority
import com.mahan.compose.jettodo.data.models.TodoTask
import com.mahan.compose.jettodo.util.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val repository: TodoRepository,
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {

    // All Tasks
    private val _tasks = MutableStateFlow<RequestState<List<TodoTask>>>(RequestState.Idle)
    val tasks: StateFlow<RequestState<List<TodoTask>>> = _tasks.asStateFlow()

    // Tasks based on user Search
    private val _searchedTasks = MutableStateFlow<RequestState<List<TodoTask>>>(RequestState.Idle)
    val searchedTasks: StateFlow<RequestState<List<TodoTask>>> = _searchedTasks.asStateFlow()

    val searchAppBarState: MutableState<SearchAppBarState> =
        mutableStateOf(SearchAppBarState.CLOSED)
    val searchAppBarText: MutableState<String> = mutableStateOf("")

    // Selected Task
    private val _selectedTask: MutableStateFlow<TodoTask?> = MutableStateFlow(null)
    val selectedTask = _selectedTask.asStateFlow()

    // TaskScreen States
    val id = mutableStateOf(0)
    val title = mutableStateOf("")
    val description = mutableStateOf("")
    val priority = mutableStateOf(Priority.Low)

    val action = mutableStateOf(Action.NO_ACTION)

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

    fun updateAction(newValue: Action) {
        if (action.value.name == newValue.name) return
        action.value = newValue
    }

    fun validateFields(): Boolean = title.value.isNotEmpty() && description.value.isNotEmpty()

    fun isDatabaseEmpty(): Boolean {
        if (tasks is RequestState.Success<*>) {
            val listOfTask = tasks.data as List<*>
            return listOfTask.isEmpty()
        }
        return false
    }


    fun getAllTasks() {
        _tasks.value = RequestState.Loading
        viewModelScope.launch {
            repository.getAllTasks().collect {
                _tasks.value = RequestState.Success(data = it)
            }
        }
    }

    fun searchDatabase() {
        _searchedTasks.value = RequestState.Loading
        try {
            viewModelScope.launch {
                repository.searchDatabase(searchQuery = "%${searchAppBarText.value}%").collect {
                    _searchedTasks.value = RequestState.Success(it)
                }
            }
        } catch (e: Exception) {
            _searchedTasks.value = RequestState.Error(e)
        }
        searchAppBarState.value = SearchAppBarState.TRIGGERED
    }

    fun getSelectedTask(taskId: Int) {
        viewModelScope.launch {
            repository.getSelectedTask(taskId = taskId).collect {
                _selectedTask.value = it
            }
        }
    }

    private fun addTask() {
        viewModelScope.launch(Dispatchers.IO) {
            val todoTask = TodoTask(
                title = title.value,
                description = description.value,
                priority = priority.value
            )
            repository.insert(todoTask)
        }
        searchAppBarState.value = SearchAppBarState.CLOSED
    }

    private fun updateTask() {
        viewModelScope.launch(Dispatchers.IO) {
            val todoTask = TodoTask(
                id = id.value,
                title = title.value,
                description = description.value,
                priority = priority.value
            )
            repository.update(todoTask)
        }
        searchAppBarState.value = SearchAppBarState.CLOSED
    }

    private fun removeTask() {
        viewModelScope.launch {
            val todoTask = TodoTask(
                id = id.value,
                title = title.value,
                description = description.value,
                priority = priority.value
            )
            repository.delete(todoTask)
        }
    }

    private fun deleteAllTasks() {
        viewModelScope.launch {
            repository.deleteAllTasks()
        }
    }

    fun handleDatabaseActions(action: Action) {
        updateAction(action)
        when (action) {
            Action.ADD -> addTask()
            Action.UPDATE -> updateTask()
            Action.DELETE -> removeTask()
            Action.DELETE_ALL -> deleteAllTasks()
            Action.UNDO -> addTask()
            else -> {}
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


    // Data Store

    private val _sortState = MutableStateFlow<RequestState<Priority>>(RequestState.Idle)
    val sortState = _sortState.asStateFlow()

    fun persistSortState(priority: Priority) {
        viewModelScope.launch {
            dataStoreRepository.persistSortState(priority)
        }
    }

    fun readSortState() {
        _sortState.value = RequestState.Loading
        viewModelScope.launch {
            dataStoreRepository.readSortState.collect {
                _sortState.value = RequestState.Success(it.toPriority())
            }
        }
    }

    val lowPriorityTasks: StateFlow<List<TodoTask>> = repository.sortByLowPriority().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        emptyList()
    )

    val highPriorityTasks: StateFlow<List<TodoTask>> = repository.sortByHighPriority().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        emptyList()
    )

}