package com.mahan.compose.jettodo.util

import android.content.Context
import android.widget.Toast
import com.mahan.compose.jettodo.data.models.Priority


fun displayToast(context: Context, message: String) =
    Toast.makeText(context, message, Toast.LENGTH_LONG).show()


// Extension Function to Return a Priority Value Based On String Value
fun String?.toPriority(): Priority {
    return when (this) {
        Priority.Low.name -> Priority.Low
        Priority.Medium.name -> Priority.Medium
        Priority.High.name -> Priority.High
        else -> Priority.None
    }
}
