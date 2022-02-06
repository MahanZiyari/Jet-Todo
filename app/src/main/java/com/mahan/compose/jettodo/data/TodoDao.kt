package com.mahan.compose.jettodo.data

import androidx.room.*
import com.mahan.compose.jettodo.data.DataBaseQueries.SELECT_ALL
import com.mahan.compose.jettodo.data.models.TodoTask
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {

    @Query(SELECT_ALL)
    fun getAllTasks(): Flow<List<TodoTask>>

    @Query(DataBaseQueries.SELECT_TASK)
    fun getSelectedTask(taskId: Int): Flow<TodoTask>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(todoTask: TodoTask)

    @Update(onConflict = OnConflictStrategy.ABORT)
    suspend fun update(todoTask: TodoTask)

    @Delete
    suspend fun delete(todoTask: TodoTask)

    @Query(DataBaseQueries.DELETE_ALL_TASKS)
    suspend fun deleteAllTasks()

    @Query(DataBaseQueries.SEARCH_DATABASE)
    fun search(searchQuery: String): Flow<List<TodoTask>>

    @Query(DataBaseQueries.SORT_BY_LOW_PRIORITY)
    fun sortByLowPriority(): Flow<List<TodoTask>>

    @Query(DataBaseQueries.SORT_BY_HIGH_PRIORITY)
    fun sortByHighPriority(): Flow<List<TodoTask>>
}