package com.mahan.compose.jettodo.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mahan.compose.jettodo.util.Constants.TODO_TABLE

@Entity(tableName = TODO_TABLE)
data class TodoTask(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String = "Untitled",
    val description: String = "Some Dummy Text",
    val priority: Priority = Priority.None
)
