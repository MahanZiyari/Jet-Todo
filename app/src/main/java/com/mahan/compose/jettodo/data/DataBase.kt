package com.mahan.compose.jettodo.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mahan.compose.jettodo.data.models.TodoTask

@Database(entities = [TodoTask::class], version = 1, exportSchema = false)
abstract class DataBase : RoomDatabase() {
    abstract fun todoDao(): TodoDao
}