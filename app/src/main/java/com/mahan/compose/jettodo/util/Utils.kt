package com.mahan.compose.jettodo.util

import android.content.Context
import android.widget.Toast
import com.mahan.compose.jettodo.data.models.Priority


fun displayToast(context: Context, message: String) =
    Toast.makeText(context, message, Toast.LENGTH_LONG).show()

// Extension Function to Return a Action Value Based On String Value
fun String?.toAction(): Action {
    return when(this) {
        Action.ADD.name -> Action.ADD
        Action.UPDATE.name -> Action.UPDATE
        Action.DELETE.name -> Action.DELETE
        Action.DELETE_ALL.name -> Action.DELETE_ALL
        Action.UNDO.name -> Action.UNDO
        else -> Action.NO_ACTION
    }
}

// Extension Function to Return a Priority Value Based On String Value
fun String?.toPriority(): Priority {
    return when(this) {
        Priority.Low.name -> Priority.Low
        Priority.Medium.name -> Priority.Medium
        Priority.High.name -> Priority.High
        else -> Priority.None
    }
}
