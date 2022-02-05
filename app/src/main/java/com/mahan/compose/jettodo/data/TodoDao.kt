package com.mahan.compose.jettodo.data

import androidx.room.*
import com.mahan.compose.jettodo.data.DataBaseQueries.SELECT_ALL
import com.mahan.compose.jettodo.data.models.TodoTask
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {

    @Query(SELECT_ALL)
    fun getAllTasks(): Flow<List<TodoTask>>

    @Query("Select * from todo_table Where id=:taskId")
    fun getSelectedTask(taskId: Int): Flow<TodoTask>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(todoTask: TodoTask)

    @Update(onConflict = OnConflictStrategy.ABORT)
    suspend fun update(todoTask: TodoTask)

    @Delete
    suspend fun delete(todoTask: TodoTask)

    @Query("Delete from todo_table")
    suspend fun deleteAllTasks()

    @Query("Select * from todo_table Where title Like :searchQuery OR description Like :searchQuery")
    fun search(searchQuery: String): Flow<List<TodoTask>>

    @Query("Select * from todo_table Order By Case When priority Like 'L%' Then 1 When priority Like 'M%' Then 2 When priority Like 'H%' Then 3 End")
    fun sortByLowPriority(): Flow<List<TodoTask>>

    @Query("Select * from todo_table Order By Case When priority Like 'H%' Then 1 When priority Like 'M%' Then 2 When priority Like 'L%' Then 3 End")
    fun sortByHighPriority(): Flow<List<TodoTask>>
}