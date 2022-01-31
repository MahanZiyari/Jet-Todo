package com.mahan.compose.jettodo.data

import com.mahan.compose.jettodo.data.models.TodoTask
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class TodoRepository @Inject constructor(private val todoDao: TodoDao) {

    fun getAllTasks() = todoDao.getAllTasks()

    fun getSelectedTask(taskId:Int) = todoDao.getSelectedTask(taskId)

    fun searchDatabase(searchQuery: String) = todoDao.search(searchQuery)
    fun sortByLowPriority() = todoDao.sortByLowPriority()

    fun sortByHighPriority() = todoDao.sortByHighPriority()

    suspend fun insert(todoTask: TodoTask) = todoDao.insert(todoTask)

    suspend fun update(todoTask: TodoTask) = todoDao.update(todoTask)

    suspend fun delete(todoTask: TodoTask) = todoDao.delete(todoTask)

    suspend fun deleteAllTasks() = todoDao.deleteAllTasks()
}